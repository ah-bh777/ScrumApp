package com.ISICOD.ScrumApp.DTOs.Espace;

import com.ISICOD.ScrumApp.Enums.RoleEspace;

public record CreateEspaceResponse(
        Integer espaceId,
        String nom,
        String nomEquipe,
        Integer capacite,
        Boolean estActive,
        Integer createurId,
        RoleEspace role
) {}