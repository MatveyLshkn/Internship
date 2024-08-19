package lma.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lma.constants.CommonConstants;
import lma.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static lma.constants.CommonConstants.AUTHORIZATION_HEADER_NAME;
import static lma.constants.CommonConstants.BEGINNING_AUTH_HEADER_NAME;
import static lma.constants.CommonConstants.SECRET;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final UserDetailsService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEGINNING_AUTH_HEADER_NAME)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authorizationHeader.substring(BEGINNING_AUTH_HEADER_NAME.length()).trim();
        if (jwt != null) {
            if(jwtProvider.validateToken(jwt, jwtProvider.getKey(), request.getRemoteAddr())) {
                String username = jwtProvider.extractUsername(jwt);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
