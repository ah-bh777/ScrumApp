package com.ISICOD.ScrumApp.DTOs.Retro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionItemRetroDTO {

    private Integer actionItemId;

    private String titre;

    private String description;

    private String statut;

    private LocalDateTime creeA;

    private Integer assigneAId;

    private String assigneANom;

    private String assigneAPrenom;

    private Integer createurId;

    private String createurNom;

    private String createurPrenom;

}