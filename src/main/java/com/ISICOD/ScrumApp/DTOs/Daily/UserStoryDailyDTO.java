package com.ISICOD.ScrumApp.DTOs.Daily;

import com.ISICOD.ScrumApp.Enums.StatutSprintBacklogItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStoryDailyDTO {

    private Integer sprintUserStoryId;

    private String titre;

    private String description;

    private String priorite;

    private Integer storyPoints;

    private StatutSprintBacklogItem statut;

    private Boolean retenue;

    private Integer estimationFinale;

}