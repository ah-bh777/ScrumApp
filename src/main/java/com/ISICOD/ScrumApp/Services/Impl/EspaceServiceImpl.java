package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.DTOs.Espace.*;
import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Repositories.EspaceRepository;
import com.ISICOD.ScrumApp.Services.EspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EspaceServiceImpl implements EspaceService {

    private final EspaceRepository espaceRepository;

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

                            // --------------------------
                            // Sprint User Stories
                            // --------------------------

                            List<SprintUserStoryDTO> sprintStories =

                                    sprint.getSprintUserStories()
                                            .stream()
                                            .map(sus -> SprintUserStoryDTO.builder()

                                                    .sprintUserStoryId(sus.getId())

                                                    .retenue(sus.getRetenue())

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

                            // --------------------------
                            // Sessions
                            // --------------------------

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

                                                                                .build())

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

                                    .userStories(
                                            sprintStories
                                    )

                                    .sessions(
                                            sessions
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
}