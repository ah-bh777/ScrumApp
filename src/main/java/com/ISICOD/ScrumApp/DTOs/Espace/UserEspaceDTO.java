package com.ISICOD.ScrumApp.DTOs.Espace;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import java.time.LocalDate;

public record UserEspaceDTO(
        Integer espaceId,
        String nom,
        String nomEquipe,
        Integer capacite,
        Boolean estActive,
        LocalDate creeA,
        RoleEspace role
) {}