package com.ISICOD.ScrumApp.DTOs.ActionItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurResumeDTO {

    private Integer id;
    private String nom;
    private String prenom;
    private String email;

}