package com.ISICOD.ScrumApp.DTOs.Espace;

import com.ISICOD.ScrumApp.Enums.StatutSprintBacklogItem;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintUserStoryDTO {

    private Integer sprintUserStoryId;

    private Boolean retenue;

    private Integer estimationFinale;

    private StatutSprintBacklogItem statut;

    private String titre;

    private Integer storyPoints;
}