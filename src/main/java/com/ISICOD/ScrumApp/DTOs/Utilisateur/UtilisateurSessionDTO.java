package com.ISICOD.ScrumApp.DTOs.Utilisateur;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import com.ISICOD.ScrumApp.Enums.RoleSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurSessionDTO {

    private Integer participantId;

    private String pseudo;

    private RoleSession roleSession;

    private Integer sessionId;

    private Integer typeSessionId;

    private String typeSessionTitre;

    private String prenom;

    private String nom;

    private String email;

    private String nomEspace;

    private String nomEquipe;

    private Integer sprintId;

    private String sprintTitre;

    private Integer utilisateurId;

    private Integer espaceId;

    private Integer capacite;

    private RoleEspace roleAttribue;


}