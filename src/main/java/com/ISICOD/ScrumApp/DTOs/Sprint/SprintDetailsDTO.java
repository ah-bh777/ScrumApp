package com.ISICOD.ScrumApp.DTOs.Sprint;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintDetailsDTO {

    private Integer sprintId;

    private String titre;

    private String objectif;

    private LocalDateTime commenceDe;

    private LocalDateTime termineA;

    private Integer capaciteMax;

    private LocalDateTime creeA;

    private SessionSummaryDTO poker;

    private SessionSummaryDTO retro;

    private List<SessionSummaryDTO> dailies;

    private List<SprintStoryDTO> userStories;
}