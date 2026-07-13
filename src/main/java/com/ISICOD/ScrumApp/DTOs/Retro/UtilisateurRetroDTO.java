package com.ISICOD.ScrumApp.DTOs.Retro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurRetroDTO {

    private Integer utilisateurId;

    private String nom;

    private String prenom;

    private String email;

}