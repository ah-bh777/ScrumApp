package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.ActionItem.ActionItemDetailsDTO;
import com.ISICOD.ScrumApp.DTOs.ActionItem.NotificationResumeDTO;
import com.ISICOD.ScrumApp.DTOs.ActionItem.SessionResumeDTO;
import com.ISICOD.ScrumApp.DTOs.ActionItem.UtilisateurResumeDTO;
import com.ISICOD.ScrumApp.DTOs.ActionItemsAndDailyContent.SprintActionItemsAndBlockagesDTO;
import com.ISICOD.ScrumApp.DTOs.Daily.DailyContentDTO;
import com.ISICOD.ScrumApp.DTOs.Daily.ParticipantDailyDTO;
import com.ISICOD.ScrumApp.DTOs.Daily.UserStoryDailyDTO;
import com.ISICOD.ScrumApp.DTOs.Daily.UtilisateurDailyDTO;
import com.ISICOD.ScrumApp.Entities.ActionItem;
import com.ISICOD.ScrumApp.Entities.DailyContent;
import com.ISICOD.ScrumApp.Entities.ParticipantSession;
import com.ISICOD.ScrumApp.Entities.Sprint;
import com.ISICOD.ScrumApp.Entities.SprintUserStory;
import com.ISICOD.ScrumApp.Entities.Utilisateur;
import com.ISICOD.ScrumApp.Services.Builders.SprintActionItemsAndBlockagesBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintActionItemsAndBlockagesBuilderImpl
        implements SprintActionItemsAndBlockagesBuilder {

    @Override
    public SprintActionItemsAndBlockagesDTO build(
            Sprint sprint,
            List<ActionItem> actionItems,
            List<DailyContent> blockages
    ) {

        List<ActionItemDetailsDTO> actionItemDTOs =
                actionItems == null
                        ? List.of()
                        : actionItems.stream()
                        .map(this::buildActionItem)
                        .toList();

        List<DailyContentDTO> blockageDTOs =
                blockages == null
                        ? List.of()
                        : blockages.stream()
                        .map(this::buildDailyContent)
                        .toList();

        return SprintActionItemsAndBlockagesDTO.builder()
                .sprintId(sprint.getId())
                .actionItems(actionItemDTOs)
                .blockages(blockageDTOs)
                .build();
    }


    // ============================================================
    // ACTION ITEM
    // ============================================================

    private ActionItemDetailsDTO buildActionItem(
            ActionItem actionItem
    ) {

        return ActionItemDetailsDTO.builder()

                .id(
                        actionItem.getId()
                )

                .titre(
                        actionItem.getTitre()
                )

                .description(
                        actionItem.getDescription()
                )

                .echeance(
                        actionItem.getEcheance()
                )

                .status(
                        actionItem.getStatus()
                )

                .creeA(
                        actionItem.getCreeA()
                )

                .createur(
                        buildUtilisateurResume(
                                actionItem.getCreateur()
                        )
                )

                .assigneA(
                        buildUtilisateurResume(
                                actionItem.getAssigneA()
                        )
                )

                .session(
                        actionItem.getSession() == null
                                ? null
                                : SessionResumeDTO.builder()

                                .id(
                                        actionItem.getSession().getId()
                                )

                                .commenceA(
                                        actionItem.getSession().getCommenceA()
                                )

                                .termineA(
                                        actionItem.getSession().getTermineA()
                                )

                                .status(
                                        actionItem.getSession().getStatus()
                                )

                                .build()
                )

                .notifications(
                        actionItem.getNotifications() == null
                                ? List.of()
                                : actionItem.getNotifications()
                                .stream()
                                .map(notification ->
                                        NotificationResumeDTO.builder()

                                                .id(
                                                        notification.getId()
                                                )

                                                .description(
                                                        notification.getDescription()
                                                )

                                                .envoyeA(
                                                        notification.getEnvoyeA()
                                                )

                                                .luA(
                                                        notification.getLuA()
                                                )

                                                .build()
                                )
                                .toList()
                )

                .build();
    }


    // ============================================================
    // UTILISATEUR RESUME
    // ============================================================

    private UtilisateurResumeDTO buildUtilisateurResume(
            Utilisateur utilisateur
    ) {

        if (utilisateur == null) {
            return null;
        }

        return UtilisateurResumeDTO.builder()

                .id(
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

                .build();
    }


    // ============================================================
    // DAILY CONTENT
    // ============================================================

    private DailyContentDTO buildDailyContent(
            DailyContent dailyContent
    ) {

        return DailyContentDTO.builder()

                .dailyId(
                        dailyContent.getId()
                )

                .contenu(
                        dailyContent.getContenu()
                )

                .typeContenu(
                        dailyContent.getTypeContenu()
                )

                .creeA(
                        dailyContent.getCreeA()
                )

                .participant(
                        buildParticipant(
                                dailyContent.getParticipantSession()
                        )
                )

                .utilisateur(
                        dailyContent.getParticipantSession() == null
                                ? null
                                : buildUtilisateur(
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


    // ============================================================
    // PARTICIPANT
    // ============================================================

    private ParticipantDailyDTO buildParticipant(
            ParticipantSession participant
    ) {

        if (participant == null) {
            return null;
        }

        return ParticipantDailyDTO.builder()

                .participantId(
                        participant.getId()
                )

                .pseudo(
                        participant.getPseudo()
                )

                .roleSession(
                        participant.getRoleSession()
                )

                .build();
    }


    // ============================================================
    // DAILY USER
    // ============================================================

    private UtilisateurDailyDTO buildUtilisateur(
            Utilisateur utilisateur
    ) {

        if (utilisateur == null) {
            return null;
        }

        return UtilisateurDailyDTO.builder()

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

                .build();
    }


    // ============================================================
    // USER STORY
    // ============================================================

    private UserStoryDailyDTO buildUserStory(
            SprintUserStory sprintUserStory
    ) {

        if (sprintUserStory == null) {
            return null;
        }

        return UserStoryDailyDTO.builder()

                .sprintUserStoryId(
                        sprintUserStory.getId()
                )

                .titre(
                        sprintUserStory.getUserStory().getTitre()
                )

                .description(
                        sprintUserStory.getUserStory().getDescription()
                )

                .priorite(
                        sprintUserStory.getUserStory().getPriorite()
                )

                .storyPoints(
                        sprintUserStory.getUserStory().getStoryPoints()
                )

                .statut(
                        sprintUserStory.getStatut()
                )

                .retenue(
                        null
                )

                .etatExecution(
                        sprintUserStory.getEtatExecution()
                )

                .estimationFinale(
                        sprintUserStory.getEstimationFinale()
                )

                .build();
    }
}