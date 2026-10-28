package com.devopshub.deployment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object: Request payload for triggering a new deployment execution.
 */
@Schema(description = "Payload to trigger a deployment run")
public record DeploymentTriggerRequest(
    @Schema(description = "Target project ID", example = "1")
    @NotNull(message = "Project ID is required")
    Long projectId,

    @Schema(description = "Target environment ID", example = "2")
    @NotNull(message = "Environment ID is required")
    Long environmentId,

    @Schema(description = "Release or Git commit version", example = "v1.4.2")
    @NotBlank(message = "Artifact version is required")
    @Size(min = 1, max = 100, message = "Artifact version must be between 1 and 100 characters")
    String artifactVersion,

    @Schema(description = "Operator or service account initiating the run", example = "shyam_lead")
    @NotBlank(message = "Triggered by username is required")
    String triggeredBy,

    @Schema(description = "Optional description or release note", example = "Hotfix for auth token expiration")
    String description
) {}
