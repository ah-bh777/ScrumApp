package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.Daily.DailySessionDTO;
import com.ISICOD.ScrumApp.DTOs.Daily.DailyContentDTO;
import com.ISICOD.ScrumApp.DTOs.Daily.ParticipantDailyDTO;
import com.ISICOD.ScrumApp.DTOs.Daily.UtilisateurDailyDTO;
import com.ISICOD.ScrumApp.DTOs.Daily.UserStoryDailyDTO;

import com.ISICOD.ScrumApp.Entities.*;
import com.ISICOD.ScrumApp.Services.Builders.DailyBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyBuilderImpl implements DailyBuilder {

    @Override
    public DailySessionDTO build(Session session) {

        List<DailyContentDTO> dailyContents =
                session.getContenusDaily()
                        .stream()
                        .map(this::buildDailyContent)
                        .toList();

        return DailySessionDTO.builder()

                .sessionId(session.getId())

                .commenceA(session.getCommenceA())

                .termineA(session.getTermineA())

                .statut(session.getStatus())

                .sprintId(
                        session.getSprint().getId()
                )

                .sprintTitre(
                        session.getSprint().getTitre()
                )

                .dailyContents(dailyContents)

                .build();
    }

    private DailyContentDTO buildDailyContent(DailyContent dailyContent) {

        return DailyContentDTO.builder()

                .dailyId(dailyContent.getId())
                .contenu(dailyContent.getContenu())
                .typeContenu(dailyContent.getTypeContenu())
                .creeA(dailyContent.getCreeA())

                .participant(
                        buildParticipant(
                                dailyContent.getParticipantSession()
                        )
                )

                .utilisateur(
                        buildUtilisateur(
                                dailyContent.getParticipantSession()
                                        .getUtilisateur()
                        )
                )
                .userStory(
                        buildUserStory(
                                dailyContent.getSprintUserStory()
                        )
                )

                .build();
    }

    private ParticipantDailyDTO buildParticipant(
            ParticipantSession participant) {

        return ParticipantDailyDTO.builder()
                .participantId(participant.getId())
                .pseudo(participant.getPseudo())
                .roleSession(participant.getRoleSession())
                .build();
    }

    private UtilisateurDailyDTO buildUtilisateur(
            Utilisateur utilisateur) {

        return UtilisateurDailyDTO.builder()
                .utilisateurId(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .build();
    }

    private UserStoryDailyDTO buildUserStory(SprintUserStory sprintUserStory) {

        return UserStoryDailyDTO.builder()

                .sprintUserStoryId(sprintUserStory.getId())

                .titre(
                        sprintUserStory
                                .getUserStory()
                                .getTitre()
                )

                .description(
                        sprintUserStory
                                .getUserStory()
                                .getDescription()
                )

                .priorite(
                        sprintUserStory
                                .getUserStory()
                                .getPriorite()
                )

                .storyPoints(
                        sprintUserStory
                                .getUserStory()
                                .getStoryPoints()
                )

                .statut(
                        sprintUserStory.getStatut()
                )

                .retenue(
                        sprintUserStory.getRetenue()
                )

                .estimationFinale(
                        sprintUserStory.getEstimationFinale()
                )

                .build();
    }

}