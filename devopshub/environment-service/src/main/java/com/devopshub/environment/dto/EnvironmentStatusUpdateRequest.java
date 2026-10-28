package com.devopshub.environment.dto;

import com.devopshub.environment.entity.enums.EnvironmentStatus;
import jakarta.validation.constraints.NotNull;

public record EnvironmentStatusUpdateRequest(
    @NotNull(message = "Environment status is required (HEALTHY, DEGRADED, DOWN, UNKNOWN)")
    EnvironmentStatus status
) {}
