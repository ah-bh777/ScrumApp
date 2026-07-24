package com.ISICOD.ScrumApp.DTOs.Daily;

import com.ISICOD.ScrumApp.DTOs.Common.SessionOptionDTO;
import com.ISICOD.ScrumApp.Enums.StatutSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailySessionDTO {

    private Integer sessionId;

    private LocalDateTime commenceA;

    private LocalDateTime termineA;

    private StatutSession statut;

    private Integer sprintId;

    private String sprintTitre;

    private List<SessionOptionDTO> options;

    private List<DailyContentDTO> dailyContents;
}