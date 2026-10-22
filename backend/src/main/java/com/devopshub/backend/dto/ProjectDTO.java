package com.devopshub.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Data Transfer Object representing a DevOpsHub project")
public class ProjectDTO {

    @Schema(description = "Unique ID of the project (assigned by database)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Name of the DevOps project", example = "Kubernetes Infrastructure Migration")
    @NotBlank(message = "Project name can't be empty")
    private String name;

    @Schema(description = "Current operational status of the project", example = "ACTIVE")
    @NotBlank(message = "Project status can't be empty")
    private String status;

    // creating the getter and setter and constructor
    public ProjectDTO(Long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
