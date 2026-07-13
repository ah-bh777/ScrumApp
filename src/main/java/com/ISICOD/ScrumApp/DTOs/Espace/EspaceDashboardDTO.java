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
public class EspaceDashboardDTO {

    private Integer espaceId;

    private String nom;

    private String nomEquipe;

    private Integer capacite;

    private Boolean estActive;

    private List<EspaceMemberDTO> membres;

    private List<UserStoryResumeDTO> productBacklog;

    private List<SprintDashboardDTO> sprints;
}