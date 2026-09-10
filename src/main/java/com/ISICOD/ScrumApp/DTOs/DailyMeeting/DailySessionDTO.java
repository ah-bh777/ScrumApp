package com.ISICOD.ScrumApp.DTOs.DailyMeeting;


import com.ISICOD.ScrumApp.Enums.StatutSession;
import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySessionDTO {

    private Integer sessionId;

    private String titre;

    private TypeSessionCode typeSession;

    private StatutSession statut;

    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;
}