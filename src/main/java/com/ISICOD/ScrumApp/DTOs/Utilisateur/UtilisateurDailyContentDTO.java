package com.ISICOD.ScrumApp.DTOs.Utilisateur;

import com.ISICOD.ScrumApp.Enums.RoleSession;
import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDailyContentDTO {

    // DailyContent
    private Integer dailyContentId;
    private String contenu;
    private TypeDailyContent typeContenu;
    private LocalDateTime creeA;

    // ParticipantSession
    private Integer participantId;
    private String pseudo;
    private RoleSession roleSession;

    // Sprint
    private Integer sprintId;
    private String sprintTitre;
    private LocalDateTime commenceDe;

    // User Story
    private Integer userStoryId;
    private String userStoryTitre;
}