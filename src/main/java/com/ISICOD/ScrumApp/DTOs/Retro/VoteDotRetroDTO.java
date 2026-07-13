package com.ISICOD.ScrumApp.DTOs.Retro;

import com.ISICOD.ScrumApp.Enums.RoleSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteDotRetroDTO {

    private Integer voteId;

    private LocalDateTime creeA;

    private Integer participantId;

    private String pseudo;

    private RoleSession roleSession;

    private Integer utilisateurId;

    private String nom;

    private String prenom;

}