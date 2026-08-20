package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.Daily.ParticipantDailyDTO;
import com.ISICOD.ScrumApp.DTOs.Espace.DailyHistoryDTO;
import com.ISICOD.ScrumApp.DTOs.Espace.DailySessionHistoryDTO;
import com.ISICOD.ScrumApp.DTOs.Espace.EspaceBacklogDTO;
import com.ISICOD.ScrumApp.DTOs.Espace.UserStoryBacklogDTO;
import com.ISICOD.ScrumApp.DTOs.Espace.UserStorySprintHistoryDTO;
import com.ISICOD.ScrumApp.Entities.DailyContent;
import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Entities.ParticipantSession;
import com.ISICOD.ScrumApp.Entities.ProductBacklog;
import com.ISICOD.ScrumApp.Entities.Session;
import com.ISICOD.ScrumApp.Entities.SprintUserStory;
import com.ISICOD.ScrumApp.Entities.UserStory;
import com.ISICOD.ScrumApp.Services.Builders.EspaceBacklogBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspaceBacklogBuilderImpl
        implements EspaceBacklogBuilder {

    @Override
    public EspaceBacklogDTO build(
            Espace espace,
            ProductBacklog productBacklog
    ) {

        List<UserStoryBacklogDTO> userStories =
                productBacklog == null
                        || productBacklog.getUserStories() == null
                        ? List.of()
                        : productBacklog.getUserStories()
                        .stream()
                        .map(this::buildUserStory)
                        .toList();

        return EspaceBacklogDTO.builder()

                .espaceId(
                        espace.getId()
                )

                .nom(
                        espace.getNom()
                )

                .userStories(
                        userStories
                )

                .build();
    }

    // ============================================================
    // USER STORY
    // ============================================================

    private UserStoryBacklogDTO buildUserStory(
            UserStory userStory
    ) {

        List<UserStorySprintHistoryDTO> sprints =
                userStory.getSprintUserStories() == null
                        ? List.of()
                        : userStory.getSprintUserStories()
                        .stream()
                        .map(this::buildSprintHistory)
                        .toList();

        return UserStoryBacklogDTO.builder()

                .userStoryId(
                        userStory.getId()
                )

                .titre(
                        userStory.getTitre()
                )

                .description(
                        userStory.getDescription()
                )

                .priorite(
                        userStory.getPriorite()
                )

                .storyPoints(
                        userStory.getStoryPoints()
                )

                .sprints(
                        sprints
                )

                .build();
    }

    // ============================================================
    // SPRINT HISTORY
    // ============================================================

    private UserStorySprintHistoryDTO buildSprintHistory(
            SprintUserStory sprintUserStory
    ) {

        List<DailyHistoryDTO> dailyHistory =
                sprintUserStory.getDailyContents() == null
                        ? List.of()
                        : sprintUserStory.getDailyContents()
                        .stream()
                        .map(this::buildDailyHistory)
                        .toList();

        return UserStorySprintHistoryDTO.builder()

                .sprintId(
                        sprintUserStory.getSprint().getId()
                )

                .sprintTitre(
                        sprintUserStory.getSprint().getTitre()
                )

                .estimationFinale(
                        sprintUserStory.getEstimationFinale()
                )

                .dailyHistory(
                        dailyHistory
                )

                .build();
    }

    // ============================================================
    // DAILY HISTORY
    // ============================================================

    private DailyHistoryDTO buildDailyHistory(
            DailyContent dailyContent
    ) {

        return DailyHistoryDTO.builder()

                .dailyContentId(
                        dailyContent.getId()
                )

                .contenu(
                        dailyContent.getContenu()
                )

                .type(
                        dailyContent.getTypeContenu()
                )

                .createdAt(
                        dailyContent.getCreeA()
                )

                .participant(
                        buildParticipant(
                                dailyContent.getParticipantSession()
                        )
                )

                .session(
                        buildSession(
                                dailyContent.getSession()
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
    // SESSION
    // ============================================================

    private DailySessionHistoryDTO buildSession(
            Session session
    ) {

        if (session == null) {
            return null;
        }

        return DailySessionHistoryDTO.builder()

                .sessionId(
                        session.getId()
                )

                .commenceA(
                        session.getCommenceA()
                )

                .termineA(
                        session.getTermineA()
                )

                .type(
                        session.getTypeSession() == null
                                ? null
                                : session.getTypeSession().getCode()
                )

                .build();
    }
}