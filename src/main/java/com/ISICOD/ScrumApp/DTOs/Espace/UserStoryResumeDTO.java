package com.ISICOD.ScrumApp.DTOs.Espace;

import com.ISICOD.ScrumApp.Enums.PrioriteUserStory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStoryResumeDTO {

    private Integer id;

    private String titre;

    private PrioriteUserStory priorite;

    private Integer storyPoints;
}