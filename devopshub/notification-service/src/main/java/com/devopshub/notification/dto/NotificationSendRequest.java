package com.devopshub.notification.dto;

import com.devopshub.notification.entity.enums.NotificationChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object: Request payload for queueing a new notification alert.
 */
@Schema(description = "Payload to dispatch an alert across delivery channels")
public record NotificationSendRequest(
    @Schema(description = "Destination email or chat handle", example = "shyam_lead@company.internal")
    @NotBlank(message = "Recipient is required")
    @Size(max = 150, message = "Recipient cannot exceed 150 characters")
    String recipient,

    @Schema(description = "Alert headline or subject line", example = "Deployment Succeeded")
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
    String title,

    @Schema(description = "Full notification body or failure stack note", example = "Release v1.4.0 successfully deployed to STAGING")
    @NotBlank(message = "Message body is required")
    @Size(min = 2, max = 1000, message = "Message must be between 2 and 1000 characters")
    String message,

    @Schema(description = "Delivery transport (EMAIL, SLACK, IN_APP, WEBHOOK)", example = "SLACK")
    @NotNull(message = "Notification channel is required")
    NotificationChannel channel
) {}

