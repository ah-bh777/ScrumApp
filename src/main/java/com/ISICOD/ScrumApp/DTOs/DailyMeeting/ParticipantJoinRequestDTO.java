package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import com.ISICOD.ScrumApp.Enums.RoleSession;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantJoinRequestDTO {

    private Integer utilisateurId;

    private String pseudo;

    private RoleSession roleSession;
}