
package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyStoryReservationDTO {

    private Integer reservationId;

    private Integer sessionId;

    private Integer sprintUserStoryId;

    private Integer participantSessionId;

    private Integer utilisateurId;

    private LocalDateTime reservedAt;

    private LocalDateTime expiresAt;
}
