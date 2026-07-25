package com.ISICOD.ScrumApp.DTOs.Espace;

import com.ISICOD.ScrumApp.Enums.StatutSprint;
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

    private StatutSprint statut;

    private Integer completedStories;

    private Integer totalStories;

    private Integer progress;

//    private List<SprintUserStoryDTO> userStories;
//
//    private List<SessionResumeDTO> sessions;
}