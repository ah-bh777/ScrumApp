package com.ISICOD.ScrumApp.DTOs.Utilisateur;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import com.ISICOD.ScrumApp.Enums.StatutActionItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurRetroActionItemDTO {

    private Integer actionItemId;

    private String titre;
    private String description;

    private LocalDate echeance;

    private StatutActionItem status;

    private LocalDateTime creeA;

    // Creator
    private Integer createurId;
    private String createurNom;

    // Assigned
    private Integer assigneId;
    private String assigneNom;

    // Sprint
    private Integer sprintId;
    private String sprintTitre;

    // Session
    private Integer sessionId;
}