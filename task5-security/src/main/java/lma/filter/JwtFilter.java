package lma.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import static lma.constants.ExceptionConstants.INVALID_TOKEN_EXCEPTION_MESSAGE;
import static lma.util.JwtUtil.extractUsername;
import static lma.util.JwtUtil.isTokenValid;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

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
            if (isTokenValid(jwt, request.getRemoteAddr())) {
                String username = extractUsername(jwt);
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
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.getWriter().write(INVALID_TOKEN_EXCEPTION_MESSAGE);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}