package com.ISICOD.ScrumApp.DTOs.Daily;

import com.ISICOD.ScrumApp.Enums.RoleSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDailyDTO {

    private Integer participantId;

    private String pseudo;

    private RoleSession roleSession;

}