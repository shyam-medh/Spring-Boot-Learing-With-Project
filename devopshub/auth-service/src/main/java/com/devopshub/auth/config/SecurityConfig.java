package com.devopshub.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration: Configures BCrypt password hashing and Spring Security filter chains
 * for stateless REST API operation.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Caption: Password Encoder Bean
     * Provides an industry-standard BCryptPasswordEncoder with salt generation to securely hash passwords.
     *
     * @return PasswordEncoder BCrypt implementation instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Caption: Security Filter Chain Definition
     * Configures HTTP security rules: disables CSRF (stateless API), disables HTTP sessions,
     * permits anonymous access to /api/auth/** and Swagger documentation, and guards all other paths.
     *
     * @param http The HttpSecurity builder
     * @return Built SecurityFilterChain instance
     * @throws Exception If configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/auth/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
