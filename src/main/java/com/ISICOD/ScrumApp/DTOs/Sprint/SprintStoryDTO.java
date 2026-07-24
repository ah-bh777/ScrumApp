package com.ISICOD.ScrumApp.DTOs.Sprint;

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

    private Integer estimationFinale;

    private LocalDateTime commitA;

    private StatutSprintBacklogItem statut;

    private LocalDateTime termineA;


    private Integer userStoryId;

    private String titre;

    private String description;

    private String priorite;

    private Integer storyPoints;

    private LocalDateTime creeA;
}