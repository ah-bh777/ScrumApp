package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.Espace.EspaceMemberDTO;
import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Entities.Utilisateur;
import com.ISICOD.ScrumApp.Services.Builders.EspaceMemberBuilder;
import org.springframework.stereotype.Service;

@Service
public class EspaceMemberBuilderImpl
        implements EspaceMemberBuilder {

    @Override
    public EspaceMemberDTO build(Appartenance appartenance) {

        if (appartenance == null ||
                appartenance.getUtilisateur() == null) {

            return null;
        }

        Utilisateur utilisateur =
                appartenance.getUtilisateur();

        return EspaceMemberDTO.builder()

                .utilisateurId(
                        utilisateur.getId()
                )

                .nom(
                        utilisateur.getNom()
                )

                .prenom(
                        utilisateur.getPrenom()
                )

                .role(
                        appartenance.getRoleAttribue()
                )

                .build();
    }
}