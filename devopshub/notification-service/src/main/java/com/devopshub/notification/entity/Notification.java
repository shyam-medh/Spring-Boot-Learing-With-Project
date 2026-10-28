package com.devopshub.notification.entity;

import com.devopshub.notification.entity.enums.NotificationChannel;
import com.devopshub.notification.entity.enums.NotificationStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

/**
 * Domain Entity: Represents a notification alert dispatched across email, Slack, or in-app channels.
 * Stored in PostgreSQL database: devopshub_notifications.
 */
@Entity
@Table(
    name = "notifications",
    indexes = {
        @Index(name = "idx_notification_recipient", columnList = "recipient"),
        @Index(name = "idx_notification_status", columnList = "status"),
        @Index(name = "idx_notification_channel", columnList = "channel")
    }
)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient", nullable = false, length = 150)
    private String recipient;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "message", nullable = false, length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 50)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private NotificationStatus status;

    @Column(name = "sent_at")
    private Instant sentAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Caption: Default No-Args Constructor
     * Required by Hibernate reflection.
     */
    public Notification() {
    }

    /**
     * Caption: Parameterized Domain Constructor
     * Constructs a new notification ready for dispatch in PENDING state.
     */
    public Notification(String recipient, String title, String message, NotificationChannel channel) {
        this.recipient = recipient;
        this.title = title;
        this.message = message;
        this.channel = channel;
        this.status = NotificationStatus.PENDING;
    }

    /**
     * Caption: PrePersist Lifecycle Hook
     * Sets creation and update timestamps immediately prior to SQL INSERT.
     */
    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = NotificationStatus.PENDING;
        }
    }

    /**
     * Caption: PreUpdate Lifecycle Hook
     * Updates audit timestamp before an existing row is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", recipient='" + recipient + '\'' +
                ", title='" + title + '\'' +
                ", channel=" + channel +
                ", status=" + status +
                ", sentAt=" + sentAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

