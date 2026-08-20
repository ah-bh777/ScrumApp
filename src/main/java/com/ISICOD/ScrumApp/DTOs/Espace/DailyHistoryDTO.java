package com.ISICOD.ScrumApp.DTOs.Espace;

import com.ISICOD.ScrumApp.DTOs.Daily.ParticipantDailyDTO;
import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyHistoryDTO {

    private Integer dailyContentId;

    private String contenu;

    private TypeDailyContent type;

    private LocalDateTime createdAt;

    private ParticipantDailyDTO participant;

    private DailySessionHistoryDTO session;
}