package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import com.ISICOD.ScrumApp.Enums.StatutSession;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionStatusUpdateDTO {

    private StatutSession statut;
}