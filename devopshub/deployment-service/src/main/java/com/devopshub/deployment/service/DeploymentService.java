package com.devopshub.deployment.service;

import com.devopshub.deployment.dto.DeploymentResponse;
import com.devopshub.deployment.dto.DeploymentStatusUpdateRequest;
import com.devopshub.deployment.dto.DeploymentTriggerRequest;
import com.devopshub.deployment.entity.Deployment;
import com.devopshub.deployment.entity.enums.DeploymentStatus;
import com.devopshub.deployment.exception.InvalidDeploymentStateException;
import com.devopshub.deployment.exception.ResourceNotFoundException;
import com.devopshub.deployment.repository.DeploymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Service Layer: Encapsulates deployment pipeline orchestration, status lifecycle management,
 * and historical run queries across projects and environments.
 */
@Service
@Transactional
public class DeploymentService {

    private final DeploymentRepository deploymentRepository;

    /**
     * Caption: Constructor Injection
     * Injects the DeploymentRepository dependency.
     */
    public DeploymentService(DeploymentRepository deploymentRepository) {
        this.deploymentRepository = deploymentRepository;
    }

    /**
     * Caption: Trigger New Deployment
     * Initializes a new deployment execution run in PENDING status for the specified project and target environment.
     *
     * @param request Validated payload containing project ID, environment ID, artifact version, and trigger metadata
     * @return DeploymentResponse The newly created deployment run details
     */
    public DeploymentResponse triggerDeployment(DeploymentTriggerRequest request) {
        Deployment deployment = new Deployment(
            request.projectId(),
            request.environmentId(),
            request.artifactVersion(),
            request.triggeredBy(),
            request.description()
        );

        Deployment saved = deploymentRepository.save(deployment);
        return DeploymentResponse.from(saved);
    }

    /**
     * Caption: Fetch Deployment by ID
     * Retrieves an individual deployment execution record by its primary key ID.
     *
     * @param id The deployment record identifier
     * @return DeploymentResponse The mapped deployment details
     * @throws ResourceNotFoundException If the deployment does not exist
     */
    @Transactional(readOnly = true)
    public DeploymentResponse getDeploymentById(Long id) {
        return deploymentRepository.findById(id)
            .map(DeploymentResponse::from)
            .orElseThrow(() -> new ResourceNotFoundException("Deployment not found with ID: " + id));
    }

    /**
     * Caption: List Deployments by Project ID
     * Retrieves the complete deployment release history for a specific project, ordered newest first.
     *
     * @param projectId The project ID from project-service
     * @return List of DeploymentResponse objects
     */
    @Transactional(readOnly = true)
    public List<DeploymentResponse> getDeploymentsByProjectId(Long projectId) {
        return deploymentRepository.findByProjectIdOrderByCreatedAtDesc(projectId)
            .stream()
            .map(DeploymentResponse::from)
            .toList();
    }

    /**
     * Caption: List Deployments by Environment ID
     * Retrieves all deployment executions targeting a specific runtime environment.
     *
     * @param environmentId The target environment ID from environment-service
     * @return List of DeploymentResponse objects
     */
    @Transactional(readOnly = true)
    public List<DeploymentResponse> getDeploymentsByEnvironmentId(Long environmentId) {
        return deploymentRepository.findByEnvironmentId(environmentId)
            .stream()
            .map(DeploymentResponse::from)
            .toList();
    }

    /**
     * Caption: Update Deployment Status & Transition Timestamps
     * Transitions an active deployment run to a new lifecycle state (e.g. IN_PROGRESS, DEPLOYED, FAILED)
     * and automatically calculates startedAt and completedAt execution timestamps.
     *
     * @param id The ID of the deployment to update
     * @param request The status update request payload
     * @return DeploymentResponse The updated deployment run state
     * @throws ResourceNotFoundException If the deployment does not exist
     * @throws InvalidDeploymentStateException If an illegal status transition is requested on a finished run
     */
    public DeploymentResponse updateDeploymentStatus(Long id, DeploymentStatusUpdateRequest request) {
        Deployment deployment = deploymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Deployment not found with ID: " + id));

        DeploymentStatus current = deployment.getStatus();
        DeploymentStatus target = request.status();

        // Enforce state transition rules: Completed runs cannot transition back to active
        if ((current == DeploymentStatus.DEPLOYED || current == DeploymentStatus.FAILED || current == DeploymentStatus.CANCELLED)
                && (target == DeploymentStatus.PENDING || target == DeploymentStatus.IN_PROGRESS)) {
            throw new InvalidDeploymentStateException(
                "Cannot transition finished deployment " + id + " from " + current + " to " + target
            );
        }

        deployment.setStatus(target);

        // Record startedAt timestamp when transitioning to IN_PROGRESS
        if (target == DeploymentStatus.IN_PROGRESS && deployment.getStartedAt() == null) {
            deployment.setStartedAt(Instant.now());
        }

        // Record completedAt timestamp when entering a terminal state
        if (target == DeploymentStatus.DEPLOYED || target == DeploymentStatus.FAILED || target == DeploymentStatus.CANCELLED || target == DeploymentStatus.ROLLED_BACK) {
            if (deployment.getCompletedAt() == null) {
                deployment.setCompletedAt(Instant.now());
            }
        }

        // Update description / failure notes if supplied
        if (request.description() != null && !request.description().isBlank()) {
            deployment.setDescription(request.description());
        }

        Deployment updated = deploymentRepository.save(deployment);
        return DeploymentResponse.from(updated);
    }
}
