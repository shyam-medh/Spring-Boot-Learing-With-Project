package com.devopshub.environment.controller;

import com.devopshub.environment.dto.EnvironmentCreateRequest;
import com.devopshub.environment.dto.EnvironmentResponse;
import com.devopshub.environment.dto.EnvironmentStatusUpdateRequest;
import com.devopshub.environment.service.EnvironmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller: Exposes HTTP endpoints for environment registration,
 * status updates, catalog lookup, and deletion.
 */
@RestController
@RequestMapping("/api/environments")
@Tag(name = "Environments", description = "Target deployment environment lifecycle and status management")
public class EnvironmentController {

    private final EnvironmentService environmentService;

    /**
     * Caption: Constructor Injection
     * Injects the EnvironmentService to delegate all business logic.
     */
    public EnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    /**
     * Caption: POST /api/environments (Register Environment)
     * Handles HTTP POST requests to create a new deployment environment for a project.
     * Returns HTTP 201 Created on success.
     */
    @PostMapping
    @Operation(summary = "Register a new deployment environment", description = "Creates a new environment (e.g. DEV, STAGING, PRODUCTION) tied to a project.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Environment successfully created"),
        @ApiResponse(responseCode = "400", description = "Validation failed for request payload"),
        @ApiResponse(responseCode = "409", description = "Environment with given name already exists for the project")
    })
    public ResponseEntity<EnvironmentResponse> createEnvironment(@Valid @RequestBody EnvironmentCreateRequest request) {
        EnvironmentResponse response = environmentService.createEnvironment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Caption: GET /api/environments/{id} (Get Environment by ID)
     * Handles HTTP GET requests to fetch single environment metadata by primary key ID.
     * Returns HTTP 200 OK or 404 Not Found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Fetch an environment by ID", description = "Retrieves an environment record by its unique database identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Environment retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Environment not found")
    })
    public ResponseEntity<EnvironmentResponse> getEnvironmentById(@PathVariable Long id) {
        EnvironmentResponse response = environmentService.getEnvironmentById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Caption: GET /api/environments/project/{projectId} (List Environments by Project)
     * Handles HTTP GET requests to retrieve all deployment environments associated with a specific project.
     * Returns HTTP 200 OK with the array of environments.
     */
    @GetMapping("/project/{projectId}")
    @Operation(summary = "Fetch all environments for a project", description = "Retrieves all deployment environments registered under a specific project ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Environments retrieved successfully")
    })
    public ResponseEntity<List<EnvironmentResponse>> getEnvironmentsByProjectId(@PathVariable Long projectId) {
        List<EnvironmentResponse> responses = environmentService.getEnvironmentsByProjectId(projectId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Caption: PATCH /api/environments/{id}/status (Update Health Status)
     * Handles partial HTTP PATCH requests to update the operational status of an environment.
     * Returns HTTP 200 OK with updated entity.
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update an environment status", description = "Updates the health status of an environment (e.g. HEALTHY, DEGRADED, DOWN).")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Environment status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid status value provided"),
        @ApiResponse(responseCode = "404", description = "Environment not found")
    })
    public ResponseEntity<EnvironmentResponse> updateEnvironmentStatus(
        @PathVariable Long id,
        @Valid @RequestBody EnvironmentStatusUpdateRequest request
    ) {
        EnvironmentResponse response = environmentService.updateEnvironmentStatus(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Caption: DELETE /api/environments/{id} (Delete Environment)
     * Handles HTTP DELETE requests to remove an environment configuration permanently.
     * Returns HTTP 204 No Content.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an environment", description = "Permanently removes an environment configuration by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Environment successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Environment not found")
    })
    public void deleteEnvironment(@PathVariable Long id) {
        environmentService.deleteEnvironment(id);
    }
}
