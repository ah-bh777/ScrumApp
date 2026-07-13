package com.ISICOD.ScrumApp.DTOs.Poker;

import com.ISICOD.ScrumApp.Enums.StatutSprintBacklogItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintUserStoryPokerDTO {

    private Integer sprintUserStoryId;

    private Integer userStoryId;

    private String titre;

    private String description;

    private String priorite;

    private Integer storyPoints;

    private Integer estimationFinale;

    private Boolean retenue;

    private StatutSprintBacklogItem statut;

}