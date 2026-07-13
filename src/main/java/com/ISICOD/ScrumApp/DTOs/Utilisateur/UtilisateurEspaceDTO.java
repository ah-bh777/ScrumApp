package com.ISICOD.ScrumApp.DTOs.Utilisateur;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UtilisateurEspaceDTO {

    private Integer espaceId;
    private String nom;
    private String nomEquipe;
    private Integer capacite;
    private Boolean estActive;

    private RoleEspace role;
    private LocalDateTime rejointA;
}