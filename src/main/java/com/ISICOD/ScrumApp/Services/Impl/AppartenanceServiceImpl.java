
package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Enums.RoleEspace;
import com.ISICOD.ScrumApp.Repositories.AppartenanceRepository;
import com.ISICOD.ScrumApp.Services.AppartenanceService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppartenanceServiceImpl implements AppartenanceService {

    private final AppartenanceRepository appartenanceRepository;

    @Override
    public boolean estMembre(
            Integer utilisateurId,
            Integer espaceId
    ) {

        if (utilisateurId == null || espaceId == null) {
            return false;
        }

        return appartenanceRepository
                .findByUtilisateurIdAndEspaceId(
                        utilisateurId,
                        espaceId
                )
                .isPresent();
    }

    @Override
    public boolean possedeRole(
            Integer utilisateurId,
            Integer espaceId,
            RoleEspace role
    ) {

        if (utilisateurId == null
                || espaceId == null
                || role == null) {
            return false;
        }

        Optional<Appartenance> appartenance =
                appartenanceRepository
                        .findByUtilisateurIdAndEspaceId(
                                utilisateurId,
                                espaceId
                        );

        return appartenance
                .map(membre -> membre.getRoleAttribue() == role)
                .orElse(false);
    }

    @Override
    public boolean estScrumMaster(
            Integer utilisateurId,
            Integer espaceId
    ) {

        return possedeRole(
                utilisateurId,
                espaceId,
                RoleEspace.SCRUM_MASTER
        );
    }
}
