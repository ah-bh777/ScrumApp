package com.ISICOD.ScrumApp.Services.DailyMeeting;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyWebSocketEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyWebSocketPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publish(Integer sessionId, DailyWebSocketEventDTO event) {

        String destination = "/topic/daily/" + sessionId;

        messagingTemplate.convertAndSend(
                destination,
                event
        );
    }
}   