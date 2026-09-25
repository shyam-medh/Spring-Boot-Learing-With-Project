package com.devopshub.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devopshub.backend.entity.User;
import com.devopshub.backend.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if the user already exists so we don't accidentally create duplicates
            // every time the server restarts
            if (userRepository.findByUsername("admin").isEmpty()) {

                // 1. Create a new user
                User adminUser = new User();
                adminUser.setUsername("admin");

                // 2. ENCRYPT THE PASSWORD BEFORE SAVING!
                adminUser.setPassword(passwordEncoder.encode("supersecret"));
                adminUser.setRole("ADMIN");

                // 3. Save to PostgreSQL
                userRepository.save(adminUser);

                System.out.println("✅ Default Admin User created in PostgreSQL!");
            }
        };
    }
}
