package com.devopshub.notification.dto;

import com.devopshub.notification.entity.enums.NotificationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object: Request payload for updating the dispatch status of an alert.
 */
@Schema(description = "Payload to update notification dispatch status")
public record NotificationStatusUpdateRequest(
    @Schema(description = "Updated dispatch state (PENDING, SENT, FAILED)", example = "SENT")
    @NotNull(message = "Notification status is required")
    NotificationStatus status
) {}

