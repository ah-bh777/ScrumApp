package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.Session.ConfigurationDTO;
import com.ISICOD.ScrumApp.DTOs.Session.ParticipantDTO;
import com.ISICOD.ScrumApp.DTOs.Session.SessionDetailsDTO;
import com.ISICOD.ScrumApp.DTOs.Session.SprintDTO;
import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Entities.Session;
import com.ISICOD.ScrumApp.Entities.Sprint;
import com.ISICOD.ScrumApp.Repositories.*;
import com.ISICOD.ScrumApp.Services.Builders.SessionDetailsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionDetailsBuilderImpl
        implements SessionDetailsBuilder {

    private final AppartenanceRepository appartenanceRepository;
    
    private SprintDTO buildSprint(Session session) {

        Sprint sprint = session.getSprint();

        return SprintDTO.builder()
                .id(sprint.getId())
                .titre(sprint.getTitre())
                .objectif(sprint.getObjectif())
                .commenceDe(sprint.getCommFinanceDeDate())
                .termineA(sprint.getTermineA())
                .build();
    }

    private List<ParticipantDTO> buildParticipants(Session session) {

        return session.getParticipants()
                .stream()
                .map(participant -> {

                    Appartenance appartenance =
                            appartenanceRepository
                                    .findByUtilisateurIdAndEspaceId(
                                            participant.getUtilisateur().getId(),
                                            session.getEspace().getId()
                                    )
                                    .orElseThrow(() ->
                                            new RuntimeException(
                                                    "Appartenance introuvable"));

                    return ParticipantDTO.builder()


                            .participantId(participant.getId())
                            .pseudo(participant.getPseudo())
                            .roleSession(participant.getRoleSession())


                            .utilisateurId(participant.getUtilisateur().getId())
                            .nom(participant.getUtilisateur().getNom())
                            .prenom(participant.getUtilisateur().getPrenom())
                            .email(participant.getUtilisateur().getEmail())


                            .roleEspace(appartenance.getRoleAttribue())

                            .build();
                })
                .toList();
    }

    private List<ConfigurationDTO> buildConfigurations(Session session) {

        return session.getConfigurations()
                .stream()
                .map(configuration ->

                        ConfigurationDTO.builder()

                                .fonctionnalite(
                                        configuration
                                                .getFonctionnalite()
                                                .getName()
                                )

                                .valeur(configuration.getValeur())

                                .build()

                )
                .toList();
    }


    @Override
    public SessionDetailsDTO build(Session session) {

        SessionDetailsDTO dto = new SessionDetailsDTO();

        dto.setSessionId(session.getId());
        dto.setCommenceA(session.getCommenceA());
        dto.setTermineA(session.getTermineA());
        dto.setStatut(session.getStatus());
        dto.setTypeSession(session.getTypeSession().getCode());

        dto.setSprint(buildSprint(session));
        dto.setParticipants(buildParticipants(session));
        dto.setConfigurations(buildConfigurations(session));

        return dto;
    }
}