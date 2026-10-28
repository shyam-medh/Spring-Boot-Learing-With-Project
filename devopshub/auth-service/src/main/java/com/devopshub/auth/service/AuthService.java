package com.devopshub.auth.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devopshub.auth.dto.AuthRequest;
import com.devopshub.auth.dto.AuthResponse;
import com.devopshub.auth.dto.RegisterRequest;
import com.devopshub.auth.entity.User;
import com.devopshub.auth.exception.DuplicateResourceException;
import com.devopshub.auth.repository.UserRepository;
import com.devopshub.auth.security.JwtUtil;

/**
 * Service Layer: Encapsulates user registration, secure BCrypt password hashing,
 * credential validation, and JWT token issuance.
 */
@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * Caption: Constructor Injectiona
     * Injects UserRepository for persistence, PasswordEncoder for BCrypt hashing, and JwtUtil for token creation.
     */
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Caption: Register New User
     * Verifies that the desired username is unique, hashes the raw password using BCrypt,
     * persists the user entity to the PostgreSQL devopshub_auth database, and issues an initial JWT token.
     *
     * @param request The registration DTO containing username, raw password, and optional role
     * @return AuthResponse Containing the signed JWT token, username, and assigned role
     * @throws DuplicateResourceException If the username already exists
     */
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username '" + request.getUsername() + "' is already taken");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String role = (request.getRole() != null && !request.getRole().isBlank())
                ? request.getRole().toUpperCase()
                : "USER";

        User user = new User(request.getUsername(), encodedPassword, role);
        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getUsername(), savedUser.getRole());
        return new AuthResponse(token, savedUser.getUsername(), savedUser.getRole());
    }

    /**
     * Caption: Authenticate / Login User
     * Verifies user existence by username, compares the raw password against the stored BCrypt hash,
     * and returns a signed JWT token if authentication succeeds.
     *
     * @param request The login DTO containing username and raw password
     * @return AuthResponse Containing the signed JWT token, username, and role
     * @throws BadCredentialsException If the username does not exist or password does not match
     */
    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return new AuthResponse(token, user.getUsername(), user.getRole());
    }
}
