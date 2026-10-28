package com.devopshub.notification.dto;

import com.devopshub.notification.entity.Notification;
import com.devopshub.notification.entity.enums.NotificationChannel;
import com.devopshub.notification.entity.enums.NotificationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * Data Transfer Object: Read-only representation of a notification alert.
 */
@Schema(description = "Notification delivery response details")
public record NotificationResponse(
    Long id,
    String recipient,
    String title,
    String message,
    NotificationChannel channel,
    NotificationStatus status,
    Instant sentAt,
    Instant createdAt,
    Instant updatedAt
) {

    /**
     * Caption: Static Factory Mapper
     * Maps an internal Notification JPA entity into an immutable NotificationResponse DTO.
     *
     * @param n The Notification entity
     * @return NotificationResponse The populated response record
     */
    public static NotificationResponse from(Notification n) {
        return new NotificationResponse(
            n.getId(),
            n.getRecipient(),
            n.getTitle(),
            n.getMessage(),
            n.getChannel(),
            n.getStatus(),
            n.getSentAt(),
            n.getCreatedAt(),
            n.getUpdatedAt()
        );
    }
}

