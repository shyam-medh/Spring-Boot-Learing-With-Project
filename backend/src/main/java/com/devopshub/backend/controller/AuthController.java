package com.devopshub.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devopshub.backend.dto.AuthRequest;
import com.devopshub.backend.dto.AuthResponse;
import com.devopshub.backend.dto.RegisterRequest;
import com.devopshub.backend.entity.User;
import com.devopshub.backend.repository.UserRepository;
import com.devopshub.backend.security.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        // Step 1: Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username '" + request.getUsername() + "' is already taken!");
        }

        // Step 2: Determine role (default to "USER" if blank)
        String userRole = (request.getRole() != null && !request.getRole().trim().isEmpty())
                ? request.getRole().trim().toUpperCase()
                : "USER";

        // Step 3: Create and encrypt user password before saving to PostgreSQL
        User newUser = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                userRole
        );
        userRepository.save(newUser);

        // Step 4: Issue JWT token so user is immediately authenticated
        String token = jwtUtil.generateToken(newUser.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        // Step 1: Hand credentials to Spring's AuthenticationManager to verify against DB
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Step 2: If authentication succeeded, generate the JWT token
        String token = jwtUtil.generateToken(request.getUsername());

        // Step 3: Return the token in the response
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
