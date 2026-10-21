package com.ISICOD.ScrumApp.DTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private Integer utilisateurId;
    private String nom;
    private String prenom;
    private String email;
}