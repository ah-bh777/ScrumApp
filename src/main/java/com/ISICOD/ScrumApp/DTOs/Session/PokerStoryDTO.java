package com.ISICOD.ScrumApp.DTOs.Session;

import com.ISICOD.ScrumApp.Enums.StatutSprintBacklogItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokerStoryDTO {

    private Integer sprintUserStoryId;

    private String titre;

    private Integer estimationFinale;

    private StatutSprintBacklogItem statut;

    private List<TourDTO> tours;
}