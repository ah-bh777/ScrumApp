package com.ISICOD.ScrumApp.DTOs.Sprint;

import com.ISICOD.ScrumApp.Enums.StatutSession;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionSummaryDTO {

    private Integer sessionId;

    private StatutSession statut;

    private LocalDateTime commenceA;

    private LocalDateTime termineA;
}