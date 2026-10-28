package com.devopshub.notification.service;

import com.devopshub.notification.dto.NotificationResponse;
import com.devopshub.notification.dto.NotificationSendRequest;
import com.devopshub.notification.dto.NotificationStatusUpdateRequest;
import com.devopshub.notification.entity.Notification;
import com.devopshub.notification.entity.enums.NotificationStatus;
import com.devopshub.notification.exception.ResourceNotFoundException;
import com.devopshub.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Service Layer: Manages alert queueing, dispatch status updates, and notification history.
 */
@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;

    /**
     * Caption: Constructor Injection
     * Injects NotificationRepository for database persistence operations.
     */
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Caption: Send / Enqueue Notification
     * Constructs a new notification entity, records delivery timestamp upon successful dispatch,
     * persists the record, and returns the response DTO.
     *
     * @param request Validated payload containing recipient, title, message body, and channel
     * @return NotificationResponse The created and dispatched alert details
     */
    public NotificationResponse sendNotification(NotificationSendRequest request) {
        Notification notification = new Notification(
            request.recipient(),
            request.title(),
            request.message(),
            request.channel()
        );

        // Simulate immediate successful delivery dispatch
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(Instant.now());

        Notification saved = notificationRepository.save(notification);
        return NotificationResponse.from(saved);
    }

    /**
     * Caption: Fetch Notification by ID
     * Retrieves an individual notification alert by its primary key ID.
     *
     * @param id The notification record ID
     * @return NotificationResponse The mapped alert details
     * @throws ResourceNotFoundException If the notification does not exist
     */
    @Transactional(readOnly = true)
    public NotificationResponse getNotificationById(Long id) {
        return notificationRepository.findById(id)
            .map(NotificationResponse::from)
            .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id));
    }

    /**
     * Caption: List Notifications by Recipient
     * Retrieves all notifications sent to a user or channel, ordered newest first.
     *
     * @param recipient The recipient handle or email address
     * @return List of NotificationResponse records
     */
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByRecipient(String recipient) {
        return notificationRepository.findByRecipientOrderByCreatedAtDesc(recipient)
            .stream()
            .map(NotificationResponse::from)
            .toList();
    }

    /**
     * Caption: Update Notification Delivery Status
     * Updates delivery state (e.g. FAILED, SENT) and records sent timestamp if transitioning to SENT.
     *
     * @param id The notification record ID
     * @param request The status update request
     * @return NotificationResponse The updated notification record
     * @throws ResourceNotFoundException If the notification does not exist
     */
    public NotificationResponse updateNotificationStatus(Long id, NotificationStatusUpdateRequest request) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id));

        notification.setStatus(request.status());
        if (request.status() == NotificationStatus.SENT && notification.getSentAt() == null) {
            notification.setSentAt(Instant.now());
        }

        Notification updated = notificationRepository.save(notification);
        return NotificationResponse.from(updated);
    }
}

