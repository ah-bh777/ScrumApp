package com.ISICOD.ScrumApp.DTOs.Utilisateur;

import com.ISICOD.ScrumApp.Enums.RoleSession;
import com.ISICOD.ScrumApp.Enums.StatutSession;
import com.ISICOD.ScrumApp.Enums.StatutSprintBacklogItem;
import com.ISICOD.ScrumApp.Enums.StatutVote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurPokerEstimationDTO {

    private Integer participantId;
    private String pseudo;
    private RoleSession roleSession;

    private Integer sessionId;
    private LocalDateTime sessionCommenceA;
    private LocalDateTime sessionTermineA;
    private StatutSession statutSession;

    private Integer sprintId;
    private String sprintTitre;
    private String objectifSprint;

    private Integer sprintUserStoryId;
    private Boolean retenue;
    private StatutSprintBacklogItem statutSprintUserStory;

    private LocalDateTime commitA;

    private LocalDateTime sprintUserStoryTermineA;

    private Integer estimationFinale;

    private Integer userStoryId;
    private String titre;
    private String description;
    private String priorite;
    private Integer storyPoints;

    private Integer tourId;
    private Integer numeroTour;
    private Boolean revele;
    private Integer valeurFinale;

    private Integer voteId;
    private Integer valeurVote;
    private StatutVote statutVote;
    private LocalDateTime voteA;
}