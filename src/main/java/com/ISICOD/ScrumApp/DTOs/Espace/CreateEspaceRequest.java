package com.ISICOD.ScrumApp.DTOs.Espace;

public record CreateEspaceRequest(
        Integer utilisateurId,
        String nom,
        String nomEquipe,
        Integer capacite
) {}