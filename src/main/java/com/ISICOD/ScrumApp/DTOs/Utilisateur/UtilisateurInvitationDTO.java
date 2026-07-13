package com.ISICOD.ScrumApp.DTOs.Utilisateur;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurInvitationDTO {

    private String code;

    private LocalDateTime expireA;

    private Boolean estValide;

    private RoleEspace role;

    private String espaceNom;

    private String nomEquipe;

}