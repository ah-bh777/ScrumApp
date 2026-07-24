package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.Sprint.*;
import com.ISICOD.ScrumApp.Entities.*;
import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import com.ISICOD.ScrumApp.Services.Builders.SprintBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintBuilderImpl implements SprintBuilder {

    @Override
    public SprintDetailsDTO build(Sprint sprint) {

        List<SprintStoryDTO> stories =
                sprint.getSprintUserStories()
                        .stream()
                        .map(this::buildStory)
                        .toList();

        SessionSummaryDTO poker =
                sprint.getSessions()
                        .stream()
                        .filter(session ->
                                session.getTypeSession().getCode() == TypeSessionCode.POKER)
                        .findFirst()
                        .map(this::buildSessionSummary)
                        .orElse(null);

        SessionSummaryDTO retro =
                sprint.getSessions()
                        .stream()
                        .filter(session ->
                                session.getTypeSession().getCode() == TypeSessionCode.RETRO)
                        .findFirst()
                        .map(this::buildSessionSummary)
                        .orElse(null);

        List<SessionSummaryDTO> dailies =
                sprint.getSessions()
                        .stream()
                        .filter(session ->
                                session.getTypeSession().getCode() == TypeSessionCode.DAILY)
                        .map(this::buildSessionSummary)
                        .toList();

        return SprintDetailsDTO.builder()

                .sprintId(sprint.getId())

                .titre(sprint.getTitre())

                .objectif(sprint.getObjectif())

                .commenceDe(sprint.getCommFinanceDeDate())

                .termineA(sprint.getTermineA())

                .capaciteMax(sprint.getCapaciteMax())

                .creeA(sprint.getCreeA())

                .poker(poker)

                .retro(retro)

                .dailies(dailies)

                .userStories(stories)

                .build();
    }

    private SprintStoryDTO buildStory(SprintUserStory sprintUserStory) {

        return SprintStoryDTO.builder()

                .sprintUserStoryId(
                        sprintUserStory.getId()
                )

                .estimationFinale(
                        sprintUserStory.getEstimationFinale()
                )

                .commitA(
                        sprintUserStory.getCommitA()
                )

                .statut(
                        sprintUserStory.getStatut()
                )

                .termineA(
                        sprintUserStory.getTermineA()
                )

                .userStoryId(
                        sprintUserStory.getUserStory().getId()
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

                .creeA(
                        sprintUserStory.getUserStory().getCreeA()
                )

                .build();
    }

    private SessionSummaryDTO buildSessionSummary(Session session) {

        return SessionSummaryDTO.builder()

                .sessionId(session.getId())

                .statut(session.getStatus())

                .commenceA(session.getCommenceA())

                .termineA(session.getTermineA())

                .build();
    }

}