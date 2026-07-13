package com.ISICOD.ScrumApp.DTOs.Poker;

import com.ISICOD.ScrumApp.Enums.RoleSession;
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
public class VotePokerDTO {

    private Integer voteId;

    private Integer valeur;

    private StatutVote statut;

    private LocalDateTime creeA;

    private Integer participantId;

    private String pseudo;

    private RoleSession roleSession;

    private Integer utilisateurId;

    private String nom;

    private String prenom;

}