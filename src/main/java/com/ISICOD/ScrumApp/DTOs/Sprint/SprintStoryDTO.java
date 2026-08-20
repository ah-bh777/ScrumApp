package com.ISICOD.ScrumApp.DTOs.Sprint;

import com.ISICOD.ScrumApp.Enums.EtatExecutionSprint;
import com.ISICOD.ScrumApp.Enums.PrioriteUserStory;
import com.ISICOD.ScrumApp.Enums.StatutSprintBacklogItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintStoryDTO {

    private Integer sprintUserStoryId;

    private Integer userStoryId;

    private String titre;

    private String description;

    private PrioriteUserStory priorite;

    private Integer storyPoints;

    private Integer estimationFinale;

    private StatutSprintBacklogItem planningStatus;

    private EtatExecutionSprint executionStatus;

    private LocalDateTime commitA;

    private LocalDateTime termineA;

    private Integer assigneeId;

    private String assigneeNom;

    private String assigneePrenom;


}