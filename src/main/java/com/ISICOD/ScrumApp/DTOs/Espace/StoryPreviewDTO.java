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
public class StoryPreviewDTO {

    private Integer sprintUserStoryId;

    private String titre;

    private Integer storyPoints;

    private StatutSprintBacklogItem statut;
}