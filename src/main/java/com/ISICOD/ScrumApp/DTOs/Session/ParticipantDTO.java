package com.ISICOD.ScrumApp.DTOs.Session;

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
public class ParticipantDTO {

    private Integer participantId;

    private String pseudo;

    private RoleSession roleSession;

    private Integer utilisateurId;

    private String nom;

    private String prenom;

    private String email;

    private RoleEspace roleEspace;
}