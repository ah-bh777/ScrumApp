package com.ISICOD.ScrumApp.Services.DailyMeeting;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyWebSocketEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class DailyWebSocketPublisher {

    private final SimpMessagingTemplate messagingTemplate;


    public void publish(
            Integer sessionId,
            DailyWebSocketEventDTO event
    ) {

        String destination = "/topic/daily/" + sessionId;

        publishAfterCommit(
                destination,
                event
        );
    }


    public void publishToWorkspace(
            Integer espaceId,
            DailyWebSocketEventDTO event
    ) {

        String destination = "/topic/workspace/" + espaceId;

        publishAfterCommit(
                destination,
                event
        );
    }


    private void publishAfterCommit(
            String destination,
            DailyWebSocketEventDTO event
    ) {

        /*
         * If a transaction is currently active,
         * wait until it successfully commits.
         */
        if (TransactionSynchronizationManager.isSynchronizationActive()) {

            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {

                        @Override
                        public void afterCommit() {

                            messagingTemplate.convertAndSend(
                                    destination,
                                    event
                            );
                        }
                    }
            );

            return;
        }

        messagingTemplate.convertAndSend(
                destination,
                event
        );
    }
}