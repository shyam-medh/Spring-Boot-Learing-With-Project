package com.devopshub.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.devopshub.backend.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    // Rule 1: BCrypt Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Rule 2: Expose the AuthenticationManager bean for AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Rule 3: The Security Filter Chain (The Club Rules)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF (we use tokens, not cookies)
            .csrf(csrf -> csrf.disable())

            // 2. Make session STATELESS (no server-side memory)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 3. Define door rules (Public vs Private vs Roles)
            .authorizeHttpRequests(auth -> auth
                // Public doors: Auth + Interactive Swagger/OpenAPI Documentation
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                // Role-based restrictions:
                .requestMatchers(HttpMethod.GET, "/api/projects/**").hasAnyRole("USER", "ADMIN") // Both can view
                .requestMatchers(HttpMethod.POST, "/api/projects/**").hasRole("ADMIN")           // Only ADMIN can create
                .requestMatchers(HttpMethod.DELETE, "/api/projects/**").hasRole("ADMIN")         // Only ADMIN can delete
                .anyRequest().authenticated()                                                    // Everything else requires login
            )

            // 4. Place our JWT scanner before Spring's default username/password check
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
