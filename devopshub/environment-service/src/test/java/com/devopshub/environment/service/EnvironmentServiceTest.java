package com.devopshub.environment.service;

import com.devopshub.environment.dto.EnvironmentCreateRequest;
import com.devopshub.environment.dto.EnvironmentResponse;
import com.devopshub.environment.dto.EnvironmentStatusUpdateRequest;
import com.devopshub.environment.entity.Environment;
import com.devopshub.environment.entity.enums.EnvironmentStatus;
import com.devopshub.environment.entity.enums.EnvironmentType;
import com.devopshub.environment.exception.DuplicateResourceException;
import com.devopshub.environment.exception.ResourceNotFoundException;
import com.devopshub.environment.repository.EnvironmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Test: EnvironmentService environment lifecycle and health management.
 */
@ExtendWith(MockitoExtension.class)
class EnvironmentServiceTest {

    @Mock
    private EnvironmentRepository environmentRepository;

    @InjectMocks
    private EnvironmentService environmentService;

    @Test
    @DisplayName("Should create environment when project ID and name are valid and unique")
    void shouldCreateEnvironment_WhenValid() {
        // GIVEN
        EnvironmentCreateRequest request = new EnvironmentCreateRequest(
            1L, "production-us", EnvironmentType.PRODUCTION, "https://prod.devopshub.internal", EnvironmentStatus.HEALTHY
        );

        when(environmentRepository.existsByProjectIdAndName(1L, "production-us")).thenReturn(false);

        Environment saved = new Environment(
            1L, "production-us", EnvironmentType.PRODUCTION, "https://prod.devopshub.internal", EnvironmentStatus.HEALTHY
        );
        saved.setId(10L);
        when(environmentRepository.save(any(Environment.class))).thenReturn(saved);

        // WHEN
        EnvironmentResponse response = environmentService.createEnvironment(request);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.name()).isEqualTo("production-us");
        assertThat(response.type()).isEqualTo(EnvironmentType.PRODUCTION);
        verify(environmentRepository, times(1)).save(any(Environment.class));
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when environment name already exists for same project")
    void shouldThrowDuplicateResourceException_WhenNameAlreadyExistsForProject() {
        // GIVEN
        EnvironmentCreateRequest request = new EnvironmentCreateRequest(
            1L, "staging", EnvironmentType.STAGING, "https://staging.devopshub.internal", EnvironmentStatus.UNKNOWN
        );
        when(environmentRepository.existsByProjectIdAndName(1L, "staging")).thenReturn(true);

        // WHEN & THEN
        assertThrows(DuplicateResourceException.class, () ->
            environmentService.createEnvironment(request)
        );
        verify(environmentRepository, never()).save(any(Environment.class));
    }

    @Test
    @DisplayName("Should fetch environment by ID when found")
    void shouldReturnEnvironmentById_WhenFound() {
        // GIVEN
        Environment env = new Environment(1L, "dev-cluster", EnvironmentType.DEV, "https://dev.devopshub.internal", EnvironmentStatus.HEALTHY);
        env.setId(7L);
        when(environmentRepository.findById(7L)).thenReturn(Optional.of(env));

        // WHEN
        EnvironmentResponse response = environmentService.getEnvironmentById(7L);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(7L);
        assertThat(response.name()).isEqualTo("dev-cluster");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when environment ID does not exist")
    void shouldThrowResourceNotFoundException_WhenEnvironmentNotFound() {
        // GIVEN
        when(environmentRepository.findById(999L)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () ->
            environmentService.getEnvironmentById(999L)
        );
    }

    @Test
    @DisplayName("Should retrieve all environments registered for a specific project")
    void shouldReturnEnvironmentsByProjectId() {
        // GIVEN
        Environment e1 = new Environment(1L, "dev", EnvironmentType.DEV, "https://dev.url", EnvironmentStatus.HEALTHY);
        Environment e2 = new Environment(1L, "prod", EnvironmentType.PRODUCTION, "https://prod.url", EnvironmentStatus.HEALTHY);

        when(environmentRepository.findByProjectId(1L)).thenReturn(List.of(e1, e2));

        // WHEN
        List<EnvironmentResponse> results = environmentService.getEnvironmentsByProjectId(1L);

        // THEN
        assertThat(results).hasSize(2);
        verify(environmentRepository, times(1)).findByProjectId(1L);
    }

    @Test
    @DisplayName("Should update environment health status successfully")
    void shouldUpdateEnvironmentStatus_WhenFound() {
        // GIVEN
        Environment env = new Environment(1L, "staging", EnvironmentType.STAGING, "https://staging.url", EnvironmentStatus.HEALTHY);
        env.setId(3L);
        when(environmentRepository.findById(3L)).thenReturn(Optional.of(env));

        Environment updated = new Environment(1L, "staging", EnvironmentType.STAGING, "https://staging.url", EnvironmentStatus.DEGRADED);
        updated.setId(3L);
        when(environmentRepository.save(any(Environment.class))).thenReturn(updated);

        // WHEN
        EnvironmentResponse response = environmentService.updateEnvironmentStatus(3L, new EnvironmentStatusUpdateRequest(EnvironmentStatus.DEGRADED));

        // THEN
        assertThat(response.status()).isEqualTo(EnvironmentStatus.DEGRADED);
        verify(environmentRepository, times(1)).save(any(Environment.class));
    }

    @Test
    @DisplayName("Should delete environment when it exists")
    void shouldDeleteEnvironment_WhenFound() {
        // GIVEN
        when(environmentRepository.existsById(5L)).thenReturn(true);

        // WHEN
        environmentService.deleteEnvironment(5L);

        // THEN
        verify(environmentRepository, times(1)).deleteById(5L);
    }
}
