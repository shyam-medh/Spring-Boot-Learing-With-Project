package com.devopshub.project.controller;

import com.devopshub.project.dto.ProjectCreateRequest;
import com.devopshub.project.dto.ProjectResponse;
import com.devopshub.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST Controller: Exposes HTTP endpoints for registering and querying projects in DevOpsHub service catalog.
 */
@Tag(name = "Projects", description = "Service Catalog and Git Repository Management")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    /**
     * Caption: Constructor Injection
     * Injects ProjectService to handle business operations.
     */
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Caption: POST /api/projects (Register Project)
     * Handles HTTP POST to create a new project catalog entry.
     * Sets Location header to the newly created URI and returns HTTP 201 Created.
     */
    @Operation(summary = "Register a new project in the catalog")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Project registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failure"),
            @ApiResponse(responseCode = "409", description = "Duplicate project name or slug")
    })
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectCreateRequest request) {
        ProjectResponse created = projectService.createProject(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    /**
     * Caption: GET /api/projects (List All Projects)
     * Handles HTTP GET to fetch the entire project catalog.
     * Returns HTTP 200 OK.
     */
    @Operation(summary = "List all registered projects")
    @ApiResponse(responseCode = "200", description = "Projects retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    /**
     * Caption: GET /api/projects/{id} (Get Project by ID)
     * Handles HTTP GET to fetch project details by primary key ID.
     * Returns HTTP 200 OK or 404 Not Found.
     */
    @Operation(summary = "Get project details by numeric ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project found"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    /**
     * Caption: GET /api/projects/slug/{slug} (Get Project by Slug)
     * Handles HTTP GET to fetch project details by URL-friendly slug.
     * Returns HTTP 200 OK or 404 Not Found.
     */
    @Operation(summary = "Get project details by URL slug")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project found"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProjectResponse> getProjectBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(projectService.getProjectBySlug(slug));
    }
}
