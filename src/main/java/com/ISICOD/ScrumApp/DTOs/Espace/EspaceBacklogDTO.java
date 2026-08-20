package com.ISICOD.ScrumApp.DTOs.Espace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspaceBacklogDTO {

    private Integer espaceId;

    private String nom;

    private List<UserStoryBacklogDTO> userStories;
}