package com.devopshub.notification.controller;

import com.devopshub.notification.dto.NotificationResponse;
import com.devopshub.notification.dto.NotificationSendRequest;
import com.devopshub.notification.dto.NotificationStatusUpdateRequest;
import com.devopshub.notification.service.NotificationService;
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
 * REST Controller: Exposes HTTP endpoints for dispatching alerts, querying inbox history,
 * and updating message delivery statuses.
 */
@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "Multi-channel alerting, event dispatches, and delivery audit log")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * Caption: Constructor Injection
     * Injects the NotificationService dependency.
     */
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Caption: POST /api/notifications (Dispatch Notification)
     * Handles HTTP POST to send an alert to a recipient over email, Slack, or in-app channels.
     * Returns HTTP 201 Created.
     */
    @PostMapping
    @Operation(summary = "Dispatch a notification alert", description = "Queues and sends a notification message across email, Slack, or in-app channels.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Notification successfully dispatched"),
        @ApiResponse(responseCode = "400", description = "Validation failed for request payload")
    })
    public ResponseEntity<NotificationResponse> sendNotification(@Valid @RequestBody NotificationSendRequest request) {
        NotificationResponse response = notificationService.sendNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Caption: GET /api/notifications/{id} (Get Notification by ID)
     * Handles HTTP GET to fetch single alert details and delivery timestamps by ID.
     * Returns HTTP 200 OK or 404 Not Found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID", description = "Retrieves an individual notification alert by its primary key ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notification retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long id) {
        NotificationResponse response = notificationService.getNotificationById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Caption: GET /api/notifications/recipient/{recipient} (List Recipient Notifications)
     * Handles HTTP GET to retrieve notification history for a specific recipient.
     * Returns HTTP 200 OK.
     */
    @GetMapping("/recipient/{recipient}")
    @Operation(summary = "List notifications by recipient", description = "Retrieves all notification messages sent to a recipient handle or email address.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully")
    })
    public ResponseEntity<List<NotificationResponse>> getNotificationsByRecipient(@PathVariable String recipient) {
        List<NotificationResponse> responses = notificationService.getNotificationsByRecipient(recipient);
        return ResponseEntity.ok(responses);
    }

    /**
     * Caption: PATCH /api/notifications/{id}/status (Update Delivery Status)
     * Handles partial HTTP PATCH requests to update message delivery state (e.g. SENT, FAILED).
     * Returns HTTP 200 OK.
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update notification status", description = "Updates the dispatch status of an existing notification.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notification status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid status value provided"),
        @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<NotificationResponse> updateNotificationStatus(
        @PathVariable Long id,
        @Valid @RequestBody NotificationStatusUpdateRequest request
    ) {
        NotificationResponse response = notificationService.updateNotificationStatus(id, request);
        return ResponseEntity.ok(response);
    }
}

