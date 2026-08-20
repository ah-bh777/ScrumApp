package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.DTOs.Espace.*;
import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Enums.EtatExecutionSprint;
import com.ISICOD.ScrumApp.Enums.StatutSprintBacklogItem;
import com.ISICOD.ScrumApp.Repositories.AppartenanceRepository;
import com.ISICOD.ScrumApp.Repositories.EspaceRepository;
import com.ISICOD.ScrumApp.Services.Builders.EspaceMemberBuilder;
import com.ISICOD.ScrumApp.Services.Builders.EspaceMemberListBuilder;
import com.ISICOD.ScrumApp.Services.EspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EspaceServiceImpl implements EspaceService {

    private final EspaceRepository espaceRepository;
    private final AppartenanceRepository appartenanceRepository;
    private final EspaceMemberListBuilder espaceMemberListBuilder;


    @Override
    public Espace createEspace(Espace espace) {
        return espaceRepository.save(espace);
    }

    @Override
    public Optional<Espace> getEspaceById(Integer id) {
        return espaceRepository.findById(id);
    }

    @Override
    public List<Espace> getAllEspaces() {
        return espaceRepository.findAll();
    }

    @Override
    public Espace updateEspace(Integer id, Espace espace) {

        Espace existingEspace = espaceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Espace introuvable avec l'id : " + id));


        if (espace.getNom() != null) {
            existingEspace.setNom(espace.getNom());
        }

        if (espace.getNomEquipe() != null) {
            existingEspace.setNomEquipe(espace.getNomEquipe());
        }

        if (espace.getEstActive() != null) {
            existingEspace.setEstActive(espace.getEstActive());
        }

        if (espace.getCapacite() != null) {
            existingEspace.setCapacite(espace.getCapacite());
        }

        return espaceRepository.save(existingEspace);
    }

    @Override
    public void deleteEspace(Integer id) {

        Espace espace = espaceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Espace introuvable avec l'id : " + id));

        espaceRepository.delete(espace);
    }

    @Override
    public EspaceDashboardDTO getDashboard(Integer espaceId) {

        Espace espace = espaceRepository.findById(espaceId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Espace introuvable avec id : " + espaceId));

        // ===========================
        // Membres
        // ===========================

        List<EspaceMemberDTO> membres =

                espace.getAppartenances()
                        .stream()
                        .map(app -> EspaceMemberDTO.builder()

                                .utilisateurId(app.getUtilisateur().getId())

                                .nom(app.getUtilisateur().getNom())

                                .prenom(app.getUtilisateur().getPrenom())

                                .role(app.getRoleAttribue())

                                .build())

                        .toList();

        // ===========================
        // Product Backlog
        // ===========================

        List<UserStoryResumeDTO> backlog =

                espace.getProductBacklog()
                        .getUserStories()
                        .stream()

                        .map(story -> UserStoryResumeDTO.builder()

                                .id(story.getId())

                                .titre(story.getTitre())

                                .priorite(story.getPriorite())

                                .storyPoints(story.getStoryPoints())

                                .build())

                        .toList();

        // ===========================
        // Sprints
        // ===========================

        List<SprintDashboardDTO> sprints =

                espace.getSprints()

                        .stream()

                        .map(sprint -> {

                            // ==================================================
                            // OLD IMPLEMENTATION (kept for future reference)
                            // ==================================================

                        /*
                        List<SprintUserStoryDTO> sprintStories =

                                sprint.getSprintUserStories()
                                        .stream()
                                        .map(sus -> SprintUserStoryDTO.builder()

                                                .sprintUserStoryId(
                                                        sus.getId()
                                                )

                                                .estimationFinale(
                                                        sus.getEstimationFinale()
                                                )

                                                .statut(
                                                        sus.getStatut()
                                                )

                                                .titre(
                                                        sus.getUserStory().getTitre()
                                                )

                                                .storyPoints(
                                                        sus.getUserStory().getStoryPoints()
                                                )

                                                .build())

                                        .toList();
                        */

                        /*
                        List<SessionResumeDTO> sessions =

                                sprint.getSessions()
                                        .stream()
                                        .map(session -> {

                                            List<ParticipantResumeDTO> participants =

                                                    session.getParticipants()
                                                            .stream()
                                                            .map(participant ->

                                                                    ParticipantResumeDTO.builder()

                                                                            .participantId(
                                                                                    participant.getId()
                                                                            )

                                                                            .pseudo(
                                                                                    participant.getPseudo()
                                                                            )

                                                                            .prenom(
                                                                                    participant.getUtilisateur()
                                                                                            .getPrenom()
                                                                            )

                                                                            .nom(
                                                                                    participant.getUtilisateur()
                                                                                            .getNom()
                                                                            )

                                                                            .role(
                                                                                    participant.getRoleSession()
                                                                            )

                                                                            .build()

                                                            )

                                                            .toList();

                                            return SessionResumeDTO.builder()

                                                    .sessionId(
                                                            session.getId()
                                                    )

                                                    .type(
                                                            session.getTypeSession()
                                                                    .getCode()
                                                    )

                                                    .statut(
                                                            session.getStatus()
                                                    )

                                                    .participants(
                                                            participants
                                                    )

                                                    .build();

                                        })

                                        .toList();
                        */

                            // ==================================================
                            // Preview Stories
                            // ==================================================

                            List<StoryPreviewDTO> previewStories =

                                    sprint.getSprintUserStories()

                                            .stream()

                                            .limit(3)

                                            .map(sus ->

                                                    StoryPreviewDTO.builder()

                                                            .sprintUserStoryId(
                                                                    sus.getId()
                                                            )

                                                            .titre(
                                                                    sus.getUserStory()
                                                                            .getTitre()
                                                            )

                                                            .storyPoints(
                                                                    sus.getUserStory()
                                                                            .getStoryPoints()
                                                            )

                                                            .planningStatus(
                                                                    sus.getStatut()
                                                            )

                                                            .executionStatus(
                                                                    sus.getEtatExecution()
                                                            )

                                                            .build()

                                            )

                                            .toList();

                            // ==================================================
                            // Statistics
                            // ==================================================

                            int totalStories =
                                    sprint.getSprintUserStories().size();

                            int completedStories =

                                    (int) sprint.getSprintUserStories()

                                            .stream()

                                            .filter(story ->
                                                    story.getEtatExecution()
                                                            == EtatExecutionSprint.TERMINEE)

                                            .count();

                            int totalStoryPoints =

                                    sprint.getSprintUserStories()

                                            .stream()

                                            .mapToInt(story ->
                                                    story.getUserStory()
                                                            .getStoryPoints())

                                            .sum();

                            int completedStoryPoints =

                                    sprint.getSprintUserStories()

                                            .stream()

                                            .filter(story ->
                                                    story.getEtatExecution()
                                                            == EtatExecutionSprint.TERMINEE)

                                            .mapToInt(story ->
                                                    story.getUserStory()
                                                            .getStoryPoints())

                                            .sum();

                            int progress =
                                    totalStories == 0
                                            ? 0
                                            : (completedStories * 100) / totalStories;

                            // ==================================================
                            // Sprint Card
                            // ==================================================

                            return SprintDashboardDTO.builder()

                                    .sprintId(
                                            sprint.getId()
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

                                    .statut(
                                            sprint.getStatut()
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

                                    .capacite(
                                            sprint.getCapaciteMax()
                                    )

                                    .previewStories(
                                            previewStories
                                    )

                                    .build();

                        })

                        .toList();

        // ===========================
        // Final DTO
        // ===========================

        return EspaceDashboardDTO.builder()

                .espaceId(
                        espace.getId()
                )

                .nom(
                        espace.getNom()
                )

                .nomEquipe(
                        espace.getNomEquipe()
                )

                .capacite(
                        espace.getCapacite()
                )

                .estActive(
                        espace.getEstActive()
                )

                .membres(
                        membres
                )

                .productBacklog(
                        backlog
                )

                .sprints(
                        sprints
                )

                .build();
    }


    @Override
    public List<EspaceMemberListDTO> getEspaceMembers(
            Integer espaceId
    ) {

        return appartenanceRepository
                .findByEspaceId(espaceId)
                .stream()
                .map(espaceMemberListBuilder::build)
                .toList();
    }
}