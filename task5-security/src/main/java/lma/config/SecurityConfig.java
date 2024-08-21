package lma.config;

import lma.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static lma.constants.CommonConstants.ADMIN_GREETING_ENDPOINT;
import static lma.constants.CommonConstants.ADMIN_ROLE_NAME;
import static lma.constants.CommonConstants.BASE_GREETING_ENDPOINT;
import static lma.constants.CommonConstants.LOGIN_ENDPOINT;
import static lma.constants.CommonConstants.REFRESH_ENDPOINT;
import static lma.constants.CommonConstants.REGISTER_ENDPOINT;
import static lma.constants.CommonConstants.USER_GREETING_ENDPOINT;
import static lma.constants.CommonConstants.USER_ROLE_NAME;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(BASE_GREETING_ENDPOINT,
                                        LOGIN_ENDPOINT,
                                        REGISTER_ENDPOINT,
                                        REFRESH_ENDPOINT).permitAll()
                                .requestMatchers(ADMIN_GREETING_ENDPOINT).hasAuthority(ADMIN_ROLE_NAME)
                                .requestMatchers(USER_GREETING_ENDPOINT).hasAnyAuthority(USER_ROLE_NAME,
                                        ADMIN_ROLE_NAME)
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}