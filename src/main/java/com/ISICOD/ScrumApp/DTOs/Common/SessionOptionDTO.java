package com.ISICOD.ScrumApp.DTOs.Common;


import com.ISICOD.ScrumApp.Enums.FonctionnaliteCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionOptionDTO {

    private Integer fonctionnaliteId;
    private FonctionnaliteCode code;
    private String nom;
    private String valeur;
}