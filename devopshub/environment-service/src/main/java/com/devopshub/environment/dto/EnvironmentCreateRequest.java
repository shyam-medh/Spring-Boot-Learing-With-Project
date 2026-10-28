package com.devopshub.environment.dto;

import com.devopshub.environment.entity.enums.EnvironmentStatus;
import com.devopshub.environment.entity.enums.EnvironmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnvironmentCreateRequest(
    @NotNull(message = "Project ID is required")
    Long projectId,

    @NotBlank(message = "Environment name is required")
    @Size(min = 2, max = 100, message = "Environment name must be between 2 and 100 characters")
    String name,

    @NotNull(message = "Environment type is required (DEV, STAGING, PRODUCTION, QA)")
    EnvironmentType type,

    @NotBlank(message = "Server URL is required")
    @Pattern(regexp = "^https?://.*", message = "Server URL must be a valid HTTP or HTTPS address")
    String serverUrl,

    EnvironmentStatus status
) {}
