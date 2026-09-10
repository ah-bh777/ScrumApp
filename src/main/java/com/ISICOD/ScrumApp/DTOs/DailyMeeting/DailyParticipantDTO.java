package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import com.ISICOD.ScrumApp.Enums.RoleSession;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyParticipantDTO {

    private Integer participantId;

    private Integer utilisateurId;

    private String pseudo;

    private RoleSession roleSession;

    private Boolean submitted;

    private LocalDateTime submittedAt;
}