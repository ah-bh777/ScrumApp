package com.ISICOD.ScrumApp.DTOs.Daily;

import com.ISICOD.ScrumApp.Enums.EtatExecutionSprint;
import com.ISICOD.ScrumApp.Enums.PrioriteUserStory;
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

    private PrioriteUserStory priorite;

    private Integer storyPoints;

    private StatutSprintBacklogItem statut;

    private Boolean retenue;

    private EtatExecutionSprint etatExecution;

    private Integer estimationFinale;

}