package com.ISICOD.ScrumApp.DTOs.Espace;

import com.ISICOD.ScrumApp.Enums.EtatExecutionSprint;
import com.ISICOD.ScrumApp.Enums.StatutSprintBacklogItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryPreviewDTO {

    private Integer sprintUserStoryId;

    private String issueKey;

    private String titre;

    // private Priorite priorite;

    private Integer storyPoints;

    private Integer estimationFinale;

    private StatutSprintBacklogItem planningStatus;

    private EtatExecutionSprint executionStatus;

    private String assigneeName;
}