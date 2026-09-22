package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import com.ISICOD.ScrumApp.Enums.DailyEventType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyWebSocketEventDTO {

    private DailyEventType event;

    private Integer sessionId;

    private DailyContentDTO content;

    private DailyParticipantDTO participant;

    private DailySessionDTO session;
}