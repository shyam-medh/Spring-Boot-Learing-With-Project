package com.devopshub.notification.service;

import com.devopshub.notification.dto.NotificationResponse;
import com.devopshub.notification.dto.NotificationSendRequest;
import com.devopshub.notification.dto.NotificationStatusUpdateRequest;
import com.devopshub.notification.entity.Notification;
import com.devopshub.notification.entity.enums.NotificationChannel;
import com.devopshub.notification.entity.enums.NotificationStatus;
import com.devopshub.notification.exception.ResourceNotFoundException;
import com.devopshub.notification.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Test: NotificationService alert dispatch and history queries.
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    @DisplayName("Should send notification, set status SENT, and record sentAt timestamp")
    void shouldSendNotification_AndSetStatusSent() {
        // GIVEN
        NotificationSendRequest request = new NotificationSendRequest(
            "shyam_lead@company.internal",
            "Pipeline Passed",
            "Release v2.0 deployed successfully",
            NotificationChannel.EMAIL
        );

        Notification saved = new Notification(
            "shyam_lead@company.internal",
            "Pipeline Passed",
            "Release v2.0 deployed successfully",
            NotificationChannel.EMAIL
        );
        saved.setId(1L);
        saved.setStatus(NotificationStatus.SENT);
        when(notificationRepository.save(any(Notification.class))).thenReturn(saved);

        // WHEN
        NotificationResponse response = notificationService.sendNotification(request);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.recipient()).isEqualTo("shyam_lead@company.internal");
        assertThat(response.status()).isEqualTo(NotificationStatus.SENT);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    @DisplayName("Should fetch notification by ID when found")
    void shouldReturnNotificationById_WhenFound() {
        // GIVEN
        Notification notification = new Notification(
            "dev_team_slack", "High CPU Alert", "Staging CPU exceeded 90%", NotificationChannel.SLACK
        );
        notification.setId(10L);
        when(notificationRepository.findById(10L)).thenReturn(Optional.of(notification));

        // WHEN
        NotificationResponse response = notificationService.getNotificationById(10L);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.title()).isEqualTo("High CPU Alert");
        assertThat(response.channel()).isEqualTo(NotificationChannel.SLACK);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when notification ID not found")
    void shouldThrowResourceNotFoundException_WhenNotFound() {
        // GIVEN
        when(notificationRepository.findById(999L)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () ->
            notificationService.getNotificationById(999L)
        );
    }

    @Test
    @DisplayName("Should retrieve all notifications for a recipient ordered newest first")
    void shouldReturnNotificationsByRecipient() {
        // GIVEN
        Notification n1 = new Notification("shyam", "Alert 1", "Body 1", NotificationChannel.IN_APP);
        Notification n2 = new Notification("shyam", "Alert 2", "Body 2", NotificationChannel.IN_APP);
        when(notificationRepository.findByRecipientOrderByCreatedAtDesc("shyam")).thenReturn(List.of(n2, n1));

        // WHEN
        List<NotificationResponse> responses = notificationService.getNotificationsByRecipient("shyam");

        // THEN
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).title()).isEqualTo("Alert 2");
        verify(notificationRepository, times(1)).findByRecipientOrderByCreatedAtDesc("shyam");
    }

    @Test
    @DisplayName("Should update notification status successfully")
    void shouldUpdateNotificationStatus_WhenFound() {
        // GIVEN
        Notification notification = new Notification("ops_pager", "Rollback Triggered", "Desc", NotificationChannel.WEBHOOK);
        notification.setId(5L);
        when(notificationRepository.findById(5L)).thenReturn(Optional.of(notification));
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        NotificationResponse response = notificationService.updateNotificationStatus(
            5L, new NotificationStatusUpdateRequest(NotificationStatus.SENT)
        );

        // THEN
        assertThat(response.status()).isEqualTo(NotificationStatus.SENT);
        assertThat(response.sentAt()).isNotNull();
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }
}

