package com.devopshub.auth.controller;

import com.devopshub.auth.dto.AuthRequest;
import com.devopshub.auth.dto.AuthResponse;
import com.devopshub.auth.dto.RegisterRequest;
import com.devopshub.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller: Exposes public authentication endpoints for user onboarding and token generation.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User registration, identity management, and JWT token issuance")
public class AuthController {

    private final AuthService authService;

    /**
     * Caption: Constructor Injection
     * Injects the AuthService dependency.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Caption: POST /api/auth/register (User Registration)
     * Handles HTTP POST requests to create a new user account with hashed password and issue JWT token.
     * Returns HTTP 201 Created on success.
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with BCrypt password hashing and returns an initial JWT token.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Validation failed for request body"),
        @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Caption: POST /api/auth/login (User Authentication)
     * Handles HTTP POST requests to authenticate credentials and return a signed JWT token.
     * Returns HTTP 200 OK or 401 Unauthorized.
     */
    @PostMapping("/login")
    @Operation(summary = "Authenticate user credentials", description = "Validates username and password, then issues a signed JWT token.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Authentication successful"),
        @ApiResponse(responseCode = "400", description = "Validation failed for request body"),
        @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
