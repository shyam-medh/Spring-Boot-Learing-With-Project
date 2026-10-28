package com.devopshub.deployment.dto;

import com.devopshub.deployment.entity.Deployment;
import com.devopshub.deployment.entity.enums.DeploymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * Data Transfer Object: Read-only immutable representation of a deployment run.
 */
@Schema(description = "Deployment execution response details")
public record DeploymentResponse(
    Long id,
    Long projectId,
    Long environmentId,
    String artifactVersion,
    DeploymentStatus status,
    String triggeredBy,
    String description,
    Instant startedAt,
    Instant completedAt,
    Instant createdAt,
    Instant updatedAt
) {

    /**
     * Caption: Static Factory Mapper
     * Maps an internal Deployment JPA entity into an immutable DeploymentResponse DTO.
     *
     * @param d The Deployment entity
     * @return DeploymentResponse The populated response record
     */
    public static DeploymentResponse from(Deployment d) {
        return new DeploymentResponse(
            d.getId(),
            d.getProjectId(),
            d.getEnvironmentId(),
            d.getArtifactVersion(),
            d.getStatus(),
            d.getTriggeredBy(),
            d.getDescription(),
            d.getStartedAt(),
            d.getCompletedAt(),
            d.getCreatedAt(),
            d.getUpdatedAt()
        );
    }
}
