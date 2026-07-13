package com.ISICOD.ScrumApp.DTOs.Utilisateur;

import com.ISICOD.ScrumApp.Enums.RoleSession;
import com.ISICOD.ScrumApp.Enums.TypeColonne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurVoteRetroDTO {

    // Vote
    private Integer voteId;
    private LocalDateTime voteA;

    // Note voted
    private Integer noteId;
    private String contenu;
    private TypeColonne typeColonne;

    // Author of the note
    private Integer auteurId;
    private String auteurNom;
    private String auteurPrenom;

    // Participation
    private Integer participantId;
    private String pseudo;
    private RoleSession roleSession;

    // Sprint
    private Integer sprintId;
    private String sprintTitre;
}