package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.Espace.EspaceMemberListDTO;
import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Entities.Utilisateur;
import com.ISICOD.ScrumApp.Services.Builders.EspaceMemberListBuilder;
import org.springframework.stereotype.Service;

@Service
public class EspaceMemberListBuilderImpl
        implements EspaceMemberListBuilder {

    @Override
    public EspaceMemberListDTO build(
            Appartenance appartenance
    ) {

        if (appartenance == null ||
                appartenance.getUtilisateur() == null) {

            return null;
        }

        Utilisateur utilisateur =
                appartenance.getUtilisateur();

        return EspaceMemberListDTO.builder()

                .utilisateurId(
                        utilisateur.getId()
                )

                .nom(
                        utilisateur.getNom()
                )

                .prenom(
                        utilisateur.getPrenom()
                )

                .email(
                        utilisateur.getEmail()
                )

                .initials(
                        buildInitials(
                                utilisateur
                        )
                )

                .role(
                        appartenance.getRoleAttribue()
                )

                .rejointA(
                        appartenance.getRejointA()
                )

                .status(
                        "ACTIVE"
                )

                .build();
    }

    private String buildInitials(
            Utilisateur utilisateur
    ) {

        StringBuilder initials =
                new StringBuilder();

        if (utilisateur.getPrenom() != null &&
                !utilisateur.getPrenom().isBlank()) {

            initials.append(
                    utilisateur
                            .getPrenom()
                            .trim()
                            .charAt(0)
            );
        }

        if (utilisateur.getNom() != null &&
                !utilisateur.getNom().isBlank()) {

            initials.append(
                    utilisateur
                            .getNom()
                            .trim()
                            .charAt(0)
            );
        }

        return initials
                .toString()
                .toUpperCase();
    }
}