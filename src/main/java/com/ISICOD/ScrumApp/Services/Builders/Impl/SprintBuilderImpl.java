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

        // ============================================================
        // USER STORIES
        // ============================================================

        List<SprintStoryDTO> stories =
                sprint.getSprintUserStories() == null
                        ? List.of()
                        : sprint.getSprintUserStories()
                        .stream()
                        .map(this::buildStory)
                        .toList();


        // ============================================================
        // POKERS
        // ============================================================

        List<SessionSummaryDTO> pokers =
                sprint.getSessions() == null
                        ? List.of()
                        : sprint.getSessions()
                        .stream()
                        .filter(session ->
                                session.getTypeSession() != null
                                        && session.getTypeSession().getCode()
                                        == TypeSessionCode.POKER
                        )
                        .map(this::buildSessionSummary)
                        .toList();


        // ============================================================
        // RETROS
        // ============================================================

        List<SessionSummaryDTO> retros =
                sprint.getSessions() == null
                        ? List.of()
                        : sprint.getSessions()
                        .stream()
                        .filter(session ->
                                session.getTypeSession() != null
                                        && session.getTypeSession().getCode()
                                        == TypeSessionCode.RETRO
                        )
                        .map(this::buildSessionSummary)
                        .toList();


        // ============================================================
        // DAILIES
        // ============================================================

        List<SessionSummaryDTO> dailies =
                sprint.getSessions() == null
                        ? List.of()
                        : sprint.getSessions()
                        .stream()
                        .filter(session ->
                                session.getTypeSession() != null
                                        && session.getTypeSession().getCode()
                                        == TypeSessionCode.DAILY
                        )
                        .map(this::buildSessionSummary)
                        .toList();


        // ============================================================
        // SPRINT METRICS
        // ============================================================

        int totalStories =
                sprint.getSprintUserStories() == null
                        ? 0
                        : sprint.getSprintUserStories().size();


        int completedStories =
                sprint.getSprintUserStories() == null
                        ? 0
                        : (int) sprint.getSprintUserStories()
                        .stream()
                        .filter(story ->
                                story.getEtatExecution()
                                        == EtatExecutionSprint.TERMINEE
                        )
                        .count();


        int totalStoryPoints =
                sprint.getSprintUserStories() == null
                        ? 0
                        : sprint.getSprintUserStories()
                        .stream()
                        .mapToInt(story ->
                                story.getUserStory() == null
                                        || story.getUserStory().getStoryPoints() == null
                                        ? 0
                                        : story.getUserStory().getStoryPoints()
                        )
                        .sum();


        int completedStoryPoints =
                sprint.getSprintUserStories() == null
                        ? 0
                        : sprint.getSprintUserStories()
                        .stream()
                        .filter(story ->
                                story.getEtatExecution()
                                        == EtatExecutionSprint.TERMINEE
                        )
                        .mapToInt(story ->
                                story.getUserStory() == null
                                        || story.getUserStory().getStoryPoints() == null
                                        ? 0
                                        : story.getUserStory().getStoryPoints()
                        )
                        .sum();


        int progress =
                totalStories == 0
                        ? 0
                        : (completedStories * 100) / totalStories;


        // ============================================================
        // BUILD FINAL DTO
        // ============================================================

        return SprintDetailsDTO.builder()

                .sprintId(
                        sprint.getId()
                )

                .espaceId(
                        sprint.getEspace() != null
                                ? sprint.getEspace().getId()
                                : null
                )

                .workspaceName(
                        sprint.getEspace() != null
                                ? sprint.getEspace().getNom()
                                : null
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

                // Multiple pokers
                .pokers(
                        pokers
                )

                // Multiple retros
                .retros(
                        retros
                )

                // Multiple dailies
                .dailies(
                        dailies
                )

                .userStories(
                        stories
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

                .build();
    }


    // ============================================================
    // USER STORY
    // ============================================================

    private SprintStoryDTO buildStory(
            SprintUserStory sprintUserStory
    ) {

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


    // ============================================================
    // SESSION SUMMARY
    // ============================================================

    private SessionSummaryDTO buildSessionSummary(
            Session session
    ) {

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