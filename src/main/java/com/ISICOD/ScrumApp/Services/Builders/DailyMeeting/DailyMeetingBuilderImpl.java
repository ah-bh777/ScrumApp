package com.ISICOD.ScrumApp.Services.Builders.DailyMeeting;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.*;
import com.ISICOD.ScrumApp.Entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyMeetingBuilderImpl implements DailyMeetingBuilder {

    @Override
    public DailyDTO buildDaily(
            Session session,
            List<ParticipantSession> participants,
            List<DailyContent> contents
    ) {

        return DailyDTO.builder()
                .session(buildSession(session))
                .workspace(buildWorkspace(session.getEspace()))
                .sprint(buildSprint(session.getSprint()))
                .participants(
                        participants.stream()
                                .map(participant ->
                                        buildParticipant(participant, contents)
                                )
                                .toList()
                )
                .contents(
                        contents.stream()
                                .map(this::buildContent)
                                .toList()
                )
                .build();
    }

    @Override
    public DailySessionDTO buildSession(Session session) {

        return DailySessionDTO.builder()
                .sessionId(session.getId())
                .titre(session.getTitre())
                .typeSession(session.getTypeSession().getCode())
                .statut(session.getStatus())
                .dateDebut(session.getCommenceA())
                .dateFin(session.getTermineA())
                .build();
    }

    @Override
    public DailyWorkspaceDTO buildWorkspace(Espace espace) {

        return DailyWorkspaceDTO.builder()
                .espaceId(espace.getId())
                .nom(espace.getNom())
                .build();
    }

    @Override
    public DailySprintDTO buildSprint(Sprint sprint) {

        return DailySprintDTO.builder()
                .sprintId(sprint.getId())
                .nom(sprint.getTitre())
                .build();
    }

    @Override
    public DailyParticipantDTO buildParticipant(
            ParticipantSession participant,
            List<DailyContent> contents
    ) {

        List<DailyContent> participantContents = contents.stream()
                .filter(content ->
                        content.getParticipantSession().getId()
                                .equals(participant.getId())
                )
                .toList();

        boolean submitted = !participantContents.isEmpty();

        return DailyParticipantDTO.builder()
                .participantId(participant.getId())
                .utilisateurId(
                        participant.getUtilisateur() != null
                                ? participant.getUtilisateur().getId()
                                : null
                )
                .pseudo(participant.getPseudo())
                .roleSession(participant.getRoleSession())
                .submitted(submitted)
                .submittedAt(
                        submitted
                                ? participantContents.stream()
                                .map(DailyContent::getCreeA)
                                .max(java.util.Comparator.naturalOrder())
                                .orElse(null)
                                : null
                )
                .build();
    }

    @Override
    public DailyContentDTO buildContent(DailyContent content) {

        SprintUserStory sprintUserStory = content.getSprintUserStory();

        return DailyContentDTO.builder()
                .dailyContentId(content.getId())
                .participantId(content.getParticipantSession().getId())
                .sprintUserStoryId(sprintUserStory.getId())
                .userStoryId(
                        sprintUserStory.getUserStory().getId()
                )
                .userStoryTitre(
                        sprintUserStory.getUserStory().getTitre()
                )
                .type(content.getTypeContenu())
                .description(content.getContenu())
                .createdAt(content.getCreeA())
                .build();
    }

    @Override
    public DailyStoryOptionDTO buildStoryOption(
            SprintUserStory sprintUserStory
    ) {

        UserStory userStory = sprintUserStory.getUserStory();

        return DailyStoryOptionDTO.builder()
                .sprintUserStoryId(sprintUserStory.getId())
                .userStoryId(userStory.getId())
                .titre(userStory.getTitre())
                .description(userStory.getDescription())
                .priorite(userStory.getPriorite())
                .storyPoints(userStory.getStoryPoints())
                .etatExecution(sprintUserStory.getEtatExecution())
                .build();
    }
}