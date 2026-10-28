package com.devopshub.environment.dto;

import com.devopshub.environment.entity.Environment;
import com.devopshub.environment.entity.enums.EnvironmentStatus;
import com.devopshub.environment.entity.enums.EnvironmentType;

import java.time.Instant;

/**
 * Data Transfer Object: Read-only immutable record representing an environment returned by the API.
 */
public record EnvironmentResponse(
    Long id,
    Long projectId,
    String name,
    EnvironmentType type,
    String serverUrl,
    EnvironmentStatus status,
    Instant createdAt,
    Instant updatedAt
) {

    /**
     * Caption: Static Factory Mapper
     * Maps an internal Environment JPA entity into a public EnvironmentResponse DTO.
     *
     * @param env The Environment entity from the database
     * @return EnvironmentResponse The populated immutable record
     */
    public static EnvironmentResponse from(Environment env) {
        return new EnvironmentResponse(
            env.getId(),
            env.getProjectId(),
            env.getName(),
            env.getType(),
            env.getServerUrl(),
            env.getStatus(),
            env.getCreatedAt(),
            env.getUpdatedAt()
        );
    }
}
