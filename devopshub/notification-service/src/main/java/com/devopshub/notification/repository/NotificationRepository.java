package com.devopshub.notification.repository;

import com.devopshub.notification.entity.Notification;
import com.devopshub.notification.entity.enums.NotificationChannel;
import com.devopshub.notification.entity.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository Layer: Spring Data JPA repository for notifications table in PostgreSQL devopshub_notifications.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Caption: Query Notifications by Recipient
     * Retrieves all notifications sent to a specific user or communication channel.
     *
     * @param recipient The target recipient identifier (e.g. email or Slack username)
     * @return List of matching Notification entities
     */
    List<Notification> findByRecipient(String recipient);

    /**
     * Caption: Query Notifications by Delivery Status
     * Filters alerts by their dispatch state (e.g. PENDING, SENT, FAILED).
     *
     * @param status The target NotificationStatus
     * @return List of matching Notification entities
     */
    List<Notification> findByStatus(NotificationStatus status);

    /**
     * Caption: Query Notifications by Channel
     * Filters alerts by their transport channel (e.g. EMAIL, SLACK, IN_APP).
     *
     * @param channel The delivery channel
     * @return List of matching Notification entities
     */
    List<Notification> findByChannel(NotificationChannel channel);

    /**
     * Caption: Query Recipient Notifications Ordered by Timestamp
     * Retrieves all notifications for a recipient sorted newest first.
     *
     * @param recipient The target recipient identifier
     * @return Ordered list of Notification entities
     */
    List<Notification> findByRecipientOrderByCreatedAtDesc(String recipient);
}

