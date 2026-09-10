package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import com.ISICOD.ScrumApp.Enums.EtatExecutionSprint;
import com.ISICOD.ScrumApp.Enums.PrioriteUserStory;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyStoryOptionDTO {

    private Integer sprintUserStoryId;

    private Integer userStoryId;

    private String titre;

    private String description;

    private PrioriteUserStory priorite;

    private Integer storyPoints;

    private EtatExecutionSprint etatExecution;
}