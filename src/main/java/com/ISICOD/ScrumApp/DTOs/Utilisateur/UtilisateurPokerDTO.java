package com.ISICOD.ScrumApp.DTOs.Utilisateur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurPokerDTO {

    private List<UtilisateurPokerEstimationDTO> estimations;

}