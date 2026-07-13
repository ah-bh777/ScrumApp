package com.ISICOD.ScrumApp.DTOs.Espace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintDashboardDTO {

    private Integer sprintId;

    private String titre;

    private String objectif;

    private LocalDateTime commenceDe;

    private LocalDateTime termineA;

    private List<SprintUserStoryDTO> userStories;

    private List<SessionResumeDTO> sessions;
}