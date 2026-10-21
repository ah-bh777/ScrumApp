package com.ISICOD.ScrumApp.Services.Impl.notificationPublisher;

import com.ISICOD.ScrumApp.Entities.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class NotificationEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publish(Notification notification) {

        Integer utilisateurId = notification.getUtilisateur().getId();

        String destination = "/topic/notifications/" + utilisateurId;

        Runnable send = () -> messagingTemplate.convertAndSend(
                destination,
                new NotificationEvent(
                        notification.getId(),
                        notification.getDescription(),
                        notification.getEnvoyeA(),
                        notification.getLuA(),
                        notification.getInvitation() != null
                                ? notification.getInvitation().getId()
                                : null,
                        notification.getActionItem() != null
                                ? notification.getActionItem().getId()
                                : null
                )
        );

        if (TransactionSynchronizationManager.isActualTransactionActive()
                && TransactionSynchronizationManager.isSynchronizationActive()) {

            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                            send.run();
                        }
                    }
            );
        } else {
            send.run();
        }
    }

    public record NotificationEvent(
            Integer notificationId,
            String description,
            java.time.LocalDateTime envoyeA,
            java.time.LocalDateTime luA,
            Integer invitationId,
            Integer actionItemId
    ) {
    }
}