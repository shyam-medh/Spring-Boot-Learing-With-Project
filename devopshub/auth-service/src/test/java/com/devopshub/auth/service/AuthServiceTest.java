package com.devopshub.auth.service;

import com.devopshub.auth.dto.AuthRequest;
import com.devopshub.auth.dto.AuthResponse;
import com.devopshub.auth.dto.RegisterRequest;
import com.devopshub.auth.entity.User;
import com.devopshub.auth.exception.DuplicateResourceException;
import com.devopshub.auth.repository.UserRepository;
import com.devopshub.auth.security.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Test: AuthService authentication and registration logic verification.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Should register new user and issue JWT when username is unique")
    void shouldRegisterUser_WhenUsernameIsUnique() {
        // GIVEN
        RegisterRequest request = new RegisterRequest("shyam_lead", "secret123", "ADMIN");
        when(userRepository.existsByUsername("shyam_lead")).thenReturn(false);
        when(passwordEncoder.encode("secret123")).thenReturn("$2a$10$hashedPasswordValue");

        User savedUser = new User("shyam_lead", "$2a$10$hashedPasswordValue", "ADMIN");
        savedUser.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtUtil.generateToken("shyam_lead", "ADMIN")).thenReturn("fake.jwt.token");

        // WHEN
        AuthResponse response = authService.register(request);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("fake.jwt.token");
        assertThat(response.getUsername()).isEqualTo("shyam_lead");
        assertThat(response.getRole()).isEqualTo("ADMIN");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when registering existing username")
    void shouldThrowDuplicateResourceException_WhenUsernameAlreadyExists() {
        // GIVEN
        RegisterRequest request = new RegisterRequest("existing_user", "password123", "USER");
        when(userRepository.existsByUsername("existing_user")).thenReturn(true);

        // WHEN & THEN
        assertThrows(DuplicateResourceException.class, () ->
            authService.register(request)
        );
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should authenticate and return JWT token when credentials are valid")
    void shouldAuthenticateAndReturnToken_WhenCredentialsAreValid() {
        // GIVEN
        AuthRequest request = new AuthRequest("shyam_lead", "secret123");
        User user = new User("shyam_lead", "$2a$10$hashedPasswordValue", "ADMIN");
        user.setId(1L);

        when(userRepository.findByUsername("shyam_lead")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret123", "$2a$10$hashedPasswordValue")).thenReturn(true);
        when(jwtUtil.generateToken("shyam_lead", "ADMIN")).thenReturn("valid.jwt.token");

        // WHEN
        AuthResponse response = authService.login(request);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("valid.jwt.token");
        assertThat(response.getUsername()).isEqualTo("shyam_lead");
    }

    @Test
    @DisplayName("Should throw BadCredentialsException when password does not match")
    void shouldThrowBadCredentialsException_WhenPasswordDoesNotMatch() {
        // GIVEN
        AuthRequest request = new AuthRequest("shyam_lead", "wrongPassword");
        User user = new User("shyam_lead", "$2a$10$hashedPasswordValue", "USER");

        when(userRepository.findByUsername("shyam_lead")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "$2a$10$hashedPasswordValue")).thenReturn(false);

        // WHEN & THEN
        assertThrows(BadCredentialsException.class, () ->
            authService.login(request)
        );
        verify(jwtUtil, never()).generateToken(any(), any());
    }

    @Test
    @DisplayName("Should throw BadCredentialsException when username does not exist")
    void shouldThrowBadCredentialsException_WhenUserDoesNotExist() {
        // GIVEN
        AuthRequest request = new AuthRequest("unknown_user", "password");
        when(userRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(BadCredentialsException.class, () ->
            authService.login(request)
        );
    }
}
