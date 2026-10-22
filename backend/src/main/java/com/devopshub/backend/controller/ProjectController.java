package com.devopshub.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.devopshub.backend.dto.ProjectDTO;
import com.devopshub.backend.service.ProjectService;

@Tag(name = "Projects", description = "Endpoints for managing DevOps projects and services")
@RequestMapping("/api/projects")
@RestController
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Get all projects", description = "Retrieves a list of all registered DevOps projects. Accessible by USER and ADMIN roles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of projects retrieved successfully"),
        @ApiResponse(responseCode = "403", description = "Forbidden - invalid or missing JWT token")
    })
    @GetMapping()
    public List<ProjectDTO> getAllProject() {
        return projectService.getAllProjectS();
    }

    @Operation(summary = "Create a new project", description = "Registers a new DevOps project. Restricted to ADMIN role only.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation failure - invalid request payload"),
        @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role")
    })
    @PostMapping()
    public String createProject(@Valid @RequestBody ProjectDTO project) {
        projectService.addProject(project);
        return "Project created successfully!";
    }

    @Operation(summary = "Delete project by ID", description = "Deletes an existing DevOps project by its ID. Restricted to ADMIN role only.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found with the specified ID"),
        @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role")
    })
    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable("id") Long id) {
        projectService.deleteProject(id);
        return "Project deleted successfully";
    }

    @Operation(summary = "Get project by ID", description = "Retrieves a specific DevOps project by its database ID. Accessible by USER and ADMIN roles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found with the specified ID"),
        @ApiResponse(responseCode = "403", description = "Forbidden - invalid or missing JWT token")
    })
    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable("id") Long id) {
        return projectService.getProjectById(id);
    }
}
