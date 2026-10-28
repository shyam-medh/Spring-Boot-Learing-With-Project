package com.devopshub.deployment.controller;

import com.devopshub.deployment.dto.DeploymentResponse;
import com.devopshub.deployment.dto.DeploymentStatusUpdateRequest;
import com.devopshub.deployment.dto.DeploymentTriggerRequest;
import com.devopshub.deployment.service.DeploymentService;
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
 * REST Controller: Exposes HTTP endpoints for triggering deployments, querying run history,
 * and updating pipeline run statuses.
 */
@RestController
@RequestMapping("/api/deployments")
@Tag(name = "Deployments", description = "Pipeline execution runs, release triggers, and status lifecycle management")
public class DeploymentController {

    private final DeploymentService deploymentService;

    /**
     * Caption: Constructor Injection
     * Injects the DeploymentService dependency.
     */
    public DeploymentController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    /**
     * Caption: POST /api/deployments (Trigger Deployment)
     * Handles HTTP POST to queue a new deployment execution for a project onto an environment.
     * Returns HTTP 201 Created on success.
     */
    @PostMapping
    @Operation(summary = "Trigger a new deployment", description = "Queues a new deployment run for an artifact version onto a target environment.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Deployment run successfully initialized"),
        @ApiResponse(responseCode = "400", description = "Validation failed for request payload")
    })
    public ResponseEntity<DeploymentResponse> triggerDeployment(@Valid @RequestBody DeploymentTriggerRequest request) {
        DeploymentResponse response = deploymentService.triggerDeployment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Caption: GET /api/deployments/{id} (Get Deployment by ID)
     * Handles HTTP GET to fetch metadata, timestamps, and status for a single deployment run.
     * Returns HTTP 200 OK or 404 Not Found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get deployment by ID", description = "Retrieves deployment metadata, execution timestamps, and live status.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Deployment run details retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Deployment not found")
    })
    public ResponseEntity<DeploymentResponse> getDeploymentById(@PathVariable Long id) {
        DeploymentResponse response = deploymentService.getDeploymentById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Caption: GET /api/deployments/project/{projectId} (List Deployments by Project)
     * Handles HTTP GET to fetch the complete chronological deployment history for a project.
     * Returns HTTP 200 OK.
     */
    @GetMapping("/project/{projectId}")
    @Operation(summary = "List project deployments", description = "Retrieves all deployment execution records for a specific project.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Project deployments retrieved successfully")
    })
    public ResponseEntity<List<DeploymentResponse>> getDeploymentsByProjectId(@PathVariable Long projectId) {
        List<DeploymentResponse> responses = deploymentService.getDeploymentsByProjectId(projectId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Caption: GET /api/deployments/environment/{environmentId} (List Deployments by Environment)
     * Handles HTTP GET to fetch deployment runs targeting a specific runtime environment.
     * Returns HTTP 200 OK.
     */
    @GetMapping("/environment/{environmentId}")
    @Operation(summary = "List environment deployments", description = "Retrieves all deployment execution runs targeting a specific environment.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Environment deployments retrieved successfully")
    })
    public ResponseEntity<List<DeploymentResponse>> getDeploymentsByEnvironmentId(@PathVariable Long environmentId) {
        List<DeploymentResponse> responses = deploymentService.getDeploymentsByEnvironmentId(environmentId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Caption: PATCH /api/deployments/{id}/status (Update Deployment Status)
     * Handles partial HTTP PATCH requests to transition a deployment through its lifecycle (e.g. IN_PROGRESS -> DEPLOYED).
     * Returns HTTP 200 OK with updated entity.
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update deployment status", description = "Transitions an active deployment execution to a new status (e.g. IN_PROGRESS, DEPLOYED, FAILED).")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Deployment status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid status transition or validation failure"),
        @ApiResponse(responseCode = "404", description = "Deployment not found")
    })
    public ResponseEntity<DeploymentResponse> updateDeploymentStatus(
        @PathVariable Long id,
        @Valid @RequestBody DeploymentStatusUpdateRequest request
    ) {
        DeploymentResponse response = deploymentService.updateDeploymentStatus(id, request);
        return ResponseEntity.ok(response);
    }
}
