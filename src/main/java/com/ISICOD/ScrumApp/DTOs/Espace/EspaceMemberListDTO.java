package com.ISICOD.ScrumApp.DTOs.Espace;

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
public class EspaceMemberListDTO {

    private Integer utilisateurId;

    private String nom;

    private String prenom;

    private String email;

    private String initials;

    private RoleEspace role;

    private LocalDateTime rejointA;

    private String status;
}