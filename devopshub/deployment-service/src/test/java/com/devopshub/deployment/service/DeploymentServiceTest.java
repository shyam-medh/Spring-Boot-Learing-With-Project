package com.devopshub.deployment.service;

import com.devopshub.deployment.dto.DeploymentResponse;
import com.devopshub.deployment.dto.DeploymentStatusUpdateRequest;
import com.devopshub.deployment.dto.DeploymentTriggerRequest;
import com.devopshub.deployment.entity.Deployment;
import com.devopshub.deployment.entity.enums.DeploymentStatus;
import com.devopshub.deployment.exception.InvalidDeploymentStateException;
import com.devopshub.deployment.exception.ResourceNotFoundException;
import com.devopshub.deployment.repository.DeploymentRepository;
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
 * Unit Test: DeploymentService pipeline execution and state machine rules.
 */
@ExtendWith(MockitoExtension.class)
class DeploymentServiceTest {

    @Mock
    private DeploymentRepository deploymentRepository;

    @InjectMocks
    private DeploymentService deploymentService;

    @Test
    @DisplayName("Should trigger deployment initializing in PENDING status")
    void shouldTriggerDeployment_InPendingStatus() {
        // GIVEN
        DeploymentTriggerRequest request = new DeploymentTriggerRequest(
            1L, 2L, "v2.1.0", "shyam_lead", "Production rollout"
        );

        Deployment saved = new Deployment(1L, 2L, "v2.1.0", "shyam_lead", "Production rollout");
        saved.setId(100L);
        when(deploymentRepository.save(any(Deployment.class))).thenReturn(saved);

        // WHEN
        DeploymentResponse response = deploymentService.triggerDeployment(request);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(100L);
        assertThat(response.status()).isEqualTo(DeploymentStatus.PENDING);
        assertThat(response.artifactVersion()).isEqualTo("v2.1.0");
        verify(deploymentRepository, times(1)).save(any(Deployment.class));
    }

    @Test
    @DisplayName("Should update status to IN_PROGRESS and populate startedAt timestamp")
    void shouldUpdateDeploymentStatusToInProgress_AndRecordStartedAt() {
        // GIVEN
        Deployment deployment = new Deployment(1L, 2L, "v2.1.0", "shyam_lead", "Notes");
        deployment.setId(100L);
        when(deploymentRepository.findById(100L)).thenReturn(Optional.of(deployment));
        when(deploymentRepository.save(any(Deployment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        DeploymentResponse response = deploymentService.updateDeploymentStatus(
            100L, new DeploymentStatusUpdateRequest(DeploymentStatus.IN_PROGRESS, "Building docker image")
        );

        // THEN
        assertThat(response.status()).isEqualTo(DeploymentStatus.IN_PROGRESS);
        assertThat(response.startedAt()).isNotNull();
    }

    @Test
    @DisplayName("Should update status to DEPLOYED and populate completedAt timestamp")
    void shouldUpdateDeploymentStatusToDeployed_AndRecordCompletedAt() {
        // GIVEN
        Deployment deployment = new Deployment(1L, 2L, "v2.1.0", "shyam_lead", "Notes");
        deployment.setId(100L);
        deployment.setStatus(DeploymentStatus.IN_PROGRESS);
        when(deploymentRepository.findById(100L)).thenReturn(Optional.of(deployment));
        when(deploymentRepository.save(any(Deployment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        DeploymentResponse response = deploymentService.updateDeploymentStatus(
            100L, new DeploymentStatusUpdateRequest(DeploymentStatus.DEPLOYED, "All health probes green")
        );

        // THEN
        assertThat(response.status()).isEqualTo(DeploymentStatus.DEPLOYED);
        assertThat(response.completedAt()).isNotNull();
    }

    @Test
    @DisplayName("Should throw InvalidDeploymentStateException when transitioning from terminal DEPLOYED status back to PENDING")
    void shouldThrowInvalidDeploymentStateException_WhenTransitioningFromTerminalStatus() {
        // GIVEN
        Deployment deployment = new Deployment(1L, 2L, "v2.1.0", "shyam_lead", "Notes");
        deployment.setId(100L);
        deployment.setStatus(DeploymentStatus.DEPLOYED);
        when(deploymentRepository.findById(100L)).thenReturn(Optional.of(deployment));

        // WHEN & THEN
        assertThrows(InvalidDeploymentStateException.class, () ->
            deploymentService.updateDeploymentStatus(
                100L, new DeploymentStatusUpdateRequest(DeploymentStatus.PENDING, "Re-run")
            )
        );
        verify(deploymentRepository, never()).save(any(Deployment.class));
    }

    @Test
    @DisplayName("Should list all deployments for a project ordered by createdAt desc")
    void shouldReturnDeploymentsByProjectId() {
        // GIVEN
        Deployment d1 = new Deployment(1L, 2L, "v2.1.0", "user1", "first");
        Deployment d2 = new Deployment(1L, 2L, "v2.2.0", "user2", "second");
        when(deploymentRepository.findByProjectIdOrderByCreatedAtDesc(1L)).thenReturn(List.of(d2, d1));

        // WHEN
        List<DeploymentResponse> responses = deploymentService.getDeploymentsByProjectId(1L);

        // THEN
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).artifactVersion()).isEqualTo("v2.2.0");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deployment ID not found")
    void shouldThrowResourceNotFoundException_WhenDeploymentNotFound() {
        // GIVEN
        when(deploymentRepository.findById(999L)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () ->
            deploymentService.getDeploymentById(999L)
        );
    }
}
