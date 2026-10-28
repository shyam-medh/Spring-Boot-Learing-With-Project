package com.devopshub.deployment.dto;

import com.devopshub.deployment.entity.enums.DeploymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object: Request payload for updating the status of an active deployment run.
 */
@Schema(description = "Payload to update deployment status")
public record DeploymentStatusUpdateRequest(
    @Schema(description = "Target execution status (IN_PROGRESS, DEPLOYED, FAILED, CANCELLED, ROLLED_BACK)", example = "DEPLOYED")
    @NotNull(message = "Deployment status is required")
    DeploymentStatus status,

    @Schema(description = "Optional execution notes or failure reason", example = "Health check verified after rolling restart")
    String description
) {}
