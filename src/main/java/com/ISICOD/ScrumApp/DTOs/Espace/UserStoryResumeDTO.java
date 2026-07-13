package com.ISICOD.ScrumApp.DTOs.Espace;

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

    private String priorite;

    private Integer storyPoints;
}