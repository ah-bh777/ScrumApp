package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.Sprint.*;
import com.ISICOD.ScrumApp.Entities.Session;
import com.ISICOD.ScrumApp.Entities.Sprint;
import com.ISICOD.ScrumApp.Entities.SprintUserStory;
import com.ISICOD.ScrumApp.Enums.EtatExecutionSprint;
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

        // ==========================================
        // Sprint Metrics
        // ==========================================

        int totalStories = sprint.getSprintUserStories().size();

        int completedStories =
                (int) sprint.getSprintUserStories()
                        .stream()
                        .filter(story ->
                                story.getEtatExecution() == EtatExecutionSprint.TERMINEE)
                        .count();

        int totalStoryPoints =
                sprint.getSprintUserStories()
                        .stream()
                        .mapToInt(story ->
                                story.getUserStory().getStoryPoints() == null
                                        ? 0
                                        : story.getUserStory().getStoryPoints())
                        .sum();

        int completedStoryPoints =
                sprint.getSprintUserStories()
                        .stream()
                        .filter(story ->
                                story.getEtatExecution() == EtatExecutionSprint.TERMINEE)
                        .mapToInt(story ->
                                story.getUserStory().getStoryPoints() == null
                                        ? 0
                                        : story.getUserStory().getStoryPoints())
                        .sum();

        int progress =
                totalStories == 0
                        ? 0
                        : (completedStories * 100) / totalStories;

        return SprintDetailsDTO.builder()

                .sprintId(sprint.getId())

                .espaceId(
                        sprint.getEspace().getId()
                )

                .workspaceName(
                        sprint.getEspace().getNom()
                )

                .titre(
                        sprint.getTitre()
                )

                .objectif(
                        sprint.getObjectif()
                )

                .commenceDe(
                        sprint.getCommFinanceDeDate()
                )

                .termineA(
                        sprint.getTermineA()
                )

                .capaciteMax(
                        sprint.getCapaciteMax()
                )

                .creeA(
                        sprint.getCreeA()
                )

                .poker(
                        poker
                )

                .retro(
                        retro
                )

                .dailies(
                        dailies
                )

                .completedStories(
                        completedStories
                )

                .totalStories(
                        totalStories
                )

                .completedStoryPoints(
                        completedStoryPoints
                )

                .totalStoryPoints(
                        totalStoryPoints
                )

                .progress(
                        progress
                )

                .userStories(
                        stories
                )

                .build();
    }

    private SprintStoryDTO buildStory(SprintUserStory sprintUserStory) {

        return SprintStoryDTO.builder()

                .sprintUserStoryId(
                        sprintUserStory.getId()
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

                .estimationFinale(
                        sprintUserStory.getEstimationFinale()
                )

                .planningStatus(
                        sprintUserStory.getStatut()
                )

                .executionStatus(
                        sprintUserStory.getEtatExecution()
                )

                .commitA(
                        sprintUserStory.getCommitA()
                )

                .termineA(
                        sprintUserStory.getTermineA()
                )

                .assigneeId(
                        sprintUserStory.getAssigneA() != null
                                ? sprintUserStory.getAssigneA().getId()
                                : null
                )

                .assigneeNom(
                        sprintUserStory.getAssigneA() != null
                                ? sprintUserStory.getAssigneA().getNom()
                                : null
                )

                .assigneePrenom(
                        sprintUserStory.getAssigneA() != null
                                ? sprintUserStory.getAssigneA().getPrenom()
                                : null
                )

                .build();
    }

    private SessionSummaryDTO buildSessionSummary(Session session) {

        return SessionSummaryDTO.builder()

                .sessionId(
                        session.getId()
                )

                .statut(
                        session.getStatus()
                )

                .commenceA(
                        session.getCommenceA()
                )

                .termineA(
                        session.getTermineA()
                )

                .build();
    }
}