package com.devopshub.backend.dto;

import jakarta.validation.constraints.NotBlank; // Add Import

public class ProjectDTO {
    private Long id;

    @NotBlank(message = "Project name can't be empty") // Add Rule
    private String name;

    @NotBlank(message = "Project status can't be empty") // Add Rule
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
