package com.ISICOD.ScrumApp.DTOs.Profile;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUserDTO {

    private Integer utilisateurId;

    private String nom;

    private String prenom;

    private String email;
}