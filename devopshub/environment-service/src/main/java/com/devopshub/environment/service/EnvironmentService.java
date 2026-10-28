package com.devopshub.environment.service;

import com.devopshub.environment.dto.EnvironmentCreateRequest;
import com.devopshub.environment.dto.EnvironmentResponse;
import com.devopshub.environment.dto.EnvironmentStatusUpdateRequest;
import com.devopshub.environment.entity.Environment;
import com.devopshub.environment.exception.DuplicateResourceException;
import com.devopshub.environment.exception.ResourceNotFoundException;
import com.devopshub.environment.repository.EnvironmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Layer: Manages the business logic for project deployment environments,
 * enforcing uniqueness constraints and orchestrating status transitions.
 */
@Service
@Transactional
public class EnvironmentService {

    private final EnvironmentRepository environmentRepository;

    /**
     * Caption: Constructor Injection
     * Injects the EnvironmentRepository dependency to interact with the PostgreSQL database.
     */
    public EnvironmentService(EnvironmentRepository environmentRepository) {
        this.environmentRepository = environmentRepository;
    }

    /**
     * Caption: Create Environment
     * Validates that the environment name does not already exist for the given project,
     * builds a new Environment entity, persists it to the database, and returns the response DTO.
     *
     * @param request Validated payload containing project ID, environment name, type, and URL
     * @return EnvironmentResponse The newly created and saved environment details
     * @throws DuplicateResourceException If an environment with the same name already exists for this project
     */
    public EnvironmentResponse createEnvironment(EnvironmentCreateRequest request) {
        if (environmentRepository.existsByProjectIdAndName(request.projectId(), request.name())) {
            throw new DuplicateResourceException(
                "Environment with name '" + request.name() + "' already exists for project ID " + request.projectId()
            );
        }

        Environment environment = new Environment(
            request.projectId(),
            request.name(),
            request.type(),
            request.serverUrl(),
            request.status()
        );

        Environment saved = environmentRepository.save(environment);
        return EnvironmentResponse.from(saved);
    }

    /**
     * Caption: Fetch Environment by ID
     * Retrieves an environment by its primary key database identifier.
     *
     * @param id The unique database ID of the environment
     * @return EnvironmentResponse The mapped environment data
     * @throws ResourceNotFoundException If no environment exists with the given ID
     */
    @Transactional(readOnly = true)
    public EnvironmentResponse getEnvironmentById(Long id) {
        return environmentRepository.findById(id)
            .map(EnvironmentResponse::from)
            .orElseThrow(() -> new ResourceNotFoundException("Environment not found with ID: " + id));
    }

    /**
     * Caption: Fetch All Environments for a Project
     * Queries all runtime environments (e.g., DEV, STAGING, PROD) registered under a specific project.
     *
     * @param projectId The ID of the target project in project-service
     * @return List of EnvironmentResponse objects registered for that project
     */
    @Transactional(readOnly = true)
    public List<EnvironmentResponse> getEnvironmentsByProjectId(Long projectId) {
        return environmentRepository.findByProjectId(projectId)
            .stream()
            .map(EnvironmentResponse::from)
            .toList();
    }

    /**
     * Caption: Update Environment Health Status
     * Updates the operational status (HEALTHY, DEGRADED, DOWN, UNKNOWN) of an existing environment.
     *
     * @param id The ID of the environment to update
     * @param request The new status to be applied
     * @return EnvironmentResponse The updated environment state
     * @throws ResourceNotFoundException If the environment is not found
     */
    public EnvironmentResponse updateEnvironmentStatus(Long id, EnvironmentStatusUpdateRequest request) {
        Environment environment = environmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Environment not found with ID: " + id));

        environment.setStatus(request.status());
        Environment updated = environmentRepository.save(environment);
        return EnvironmentResponse.from(updated);
    }

    /**
     * Caption: Delete Environment
     * Permanently deletes a deployment environment record by its ID.
     *
     * @param id The ID of the environment to be removed
     * @throws ResourceNotFoundException If the environment does not exist
     */
    public void deleteEnvironment(Long id) {
        if (!environmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Environment not found with ID: " + id);
        }
        environmentRepository.deleteById(id);
    }
}
