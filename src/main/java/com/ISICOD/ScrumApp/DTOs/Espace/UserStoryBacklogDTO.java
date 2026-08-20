package com.ISICOD.ScrumApp.DTOs.Espace;

import com.ISICOD.ScrumApp.Enums.PrioriteUserStory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStoryBacklogDTO {

    private Integer userStoryId;

    private String titre;

    private String description;

    private PrioriteUserStory priorite;

    private Integer storyPoints;

    private List<UserStorySprintHistoryDTO> sprints;
}