package com.ISICOD.ScrumApp.DTOs.Espace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStorySprintHistoryDTO {

    private Integer sprintId;

    private String sprintTitre;

    private Integer estimationFinale;

    private List<DailyHistoryDTO> dailyHistory;
}