package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.DTOs.Espace.*;
import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Enums.EtatExecutionSprint;
import com.ISICOD.ScrumApp.Enums.StatutSprintBacklogItem;
import com.ISICOD.ScrumApp.Exceptions.ConflictException;
import com.ISICOD.ScrumApp.Exceptions.ForbiddenException;
import com.ISICOD.ScrumApp.Repositories.AppartenanceRepository;
import com.ISICOD.ScrumApp.Repositories.EspaceRepository;
import com.ISICOD.ScrumApp.Services.Builders.EspaceMemberBuilder;
import com.ISICOD.ScrumApp.Services.Builders.EspaceMemberListBuilder;
import com.ISICOD.ScrumApp.Services.EspaceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.ISICOD.ScrumApp.DTOs.Espace.CreateEspaceRequest;
import com.ISICOD.ScrumApp.DTOs.Espace.CreateEspaceResponse;
import com.ISICOD.ScrumApp.Entities.Utilisateur;
import com.ISICOD.ScrumApp.Enums.RoleEspace;
import com.ISICOD.ScrumApp.Repositories.UtilisateurRepository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import com.ISICOD.ScrumApp.Exceptions.ResourceNotFoundException;
import com.ISICOD.ScrumApp.Exceptions.BadRequestException;
import com.ISICOD.ScrumApp.Services.AppartenanceService;

@Service
@RequiredArgsConstructor
public class EspaceServiceImpl implements EspaceService {

    private final EspaceRepository espaceRepository;
    private final AppartenanceRepository appartenanceRepository;
    private final EspaceMemberListBuilder espaceMemberListBuilder;
    private final UtilisateurRepository utilisateurRepository;
    private final EntityManager entityManager;
    private final AppartenanceService appartenanceService;


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
                        new ResourceNotFoundException(
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
                espace.getProductBacklog() == null
                        || espace.getProductBacklog().getUserStories() == null
                        ? List.of()
                        : espace.getProductBacklog()
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

    @Override
    @Transactional
    public CreateEspaceResponse createEspaceWithScrumMaster(
            CreateEspaceRequest request
    ) {

        // 1. Validate creator ID
        if (request.utilisateurId() == null || request.utilisateurId() < 1) {
            throw new BadRequestException(
                    "L'identifiant de l'utilisateur est invalide."
            );
        }

// 2. Validate workspace name
        if (request.nom() == null || request.nom().isBlank()) {
            throw new BadRequestException(
                    "Le nom de l'espace est obligatoire."
            );
        }

// 3. Validate team name (optional)
        if (request.nomEquipe() != null && request.nomEquipe().isBlank()) {
            throw new BadRequestException(
                    "Le nom de l'équipe ne peut pas être vide."
            );
        }

// 4. Validate capacity
        if (request.capacite() == null || request.capacite() < 1) {
            throw new BadRequestException(
                    "La capacité doit être supérieure à zéro."
            );
        }

        // 1. Find the creator
        Utilisateur createur = utilisateurRepository
                .findById(request.utilisateurId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Utilisateur introuvable : " + request.utilisateurId()
                ));

        // 2. Create the workspace
        Espace espace = Espace.builder()
                .nom(request.nom().trim())
                .nomEquipe(request.nomEquipe())
                .capacite(request.capacite())
                .estActive(true)
                .build();

        espace = espaceRepository.save(espace);

        // 3. Automatically assign SCRUM_MASTER
        Appartenance appartenance = Appartenance.builder()
                .utilisateur(createur)
                .espace(espace)
                .roleAttribue(RoleEspace.SCRUM_MASTER)
                .rejointA(LocalDateTime.now())
                .build();

        appartenanceRepository.save(appartenance);

        return new CreateEspaceResponse(
                espace.getId(),
                espace.getNom(),
                espace.getNomEquipe(),
                espace.getCapacite(),
                espace.getEstActive(),
                createur.getId(),
                appartenance.getRoleAttribue()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEspaceDTO> getEspacesByUtilisateur(
            Integer utilisateurId
    ) {

        if (!utilisateurRepository.existsById(utilisateurId)) {
            throw new ResourceNotFoundException(
                    "Utilisateur introuvable : " + utilisateurId
            );
        }

        return appartenanceRepository
                .getAppartenanceByUtilisateurId(utilisateurId)
                .stream()
                .map(appartenance -> {

                    Espace espace = appartenance.getEspace();

                    return new UserEspaceDTO(
                            espace.getId(),
                            espace.getNom(),
                            espace.getNomEquipe(),
                            espace.getCapacite(),
                            espace.getEstActive(),
                            espace.getCreeA(),
                            appartenance.getRoleAttribue()
                    );
                })
                .toList();
    }


    @Override
    @Transactional
    public void changerRoleMembre(
            Integer espaceId,
            Integer membreId,
            ChangeMemberRoleRequest request
    ) {
        if (espaceId == null || espaceId <= 0
                || membreId == null || membreId <= 0
                || request == null
                || request.acteurId() == null
                || request.acteurId() <= 0
                || request.role() == null) {

            throw new IllegalArgumentException(
                    "Les identifiants et le nouveau rôle sont obligatoires."
            );
        }

        Espace espace = espaceRepository.findById(espaceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Espace introuvable : " + espaceId
                ));

        if (!Boolean.TRUE.equals(espace.getEstActive())) {
            throw new ConflictException("Cet espace est inactif.");
        }

    // Verify that the actor belongs to the workspace
            if (!appartenanceService.estMembre(request.acteurId(), espaceId)) {
                throw new ForbiddenException(
                        "Vous n'appartenez pas à cet espace."
                );
            }

    // Only a Scrum Master can change member roles
            if (!appartenanceService.estScrumMaster(request.acteurId(), espaceId)) {
                throw new ForbiddenException(
                        "Seul un Scrum Master peut modifier les rôles."
                );
            }

        Appartenance membre = appartenanceRepository
                .findByUtilisateurIdAndEspaceId(membreId, espaceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ce membre n'appartient pas à cet espace."
                ));

        if (membre.getRoleAttribue() == RoleEspace.SCRUM_MASTER) {
            throw new ConflictException(
                    "Le rôle du Scrum Master ne peut pas être modifié."
            );
        }

        if (request.role() == RoleEspace.SCRUM_MASTER) {
            throw new ConflictException(
                    "Impossible d'attribuer le rôle SCRUM_MASTER."
            );
        }

        if (membre.getRoleAttribue() == request.role()) {
            throw new ConflictException(
                    "Ce membre possède déjà ce rôle."
            );
        }

        membre.setRoleAttribue(request.role());
        appartenanceRepository.save(membre);
    }


    @Override
    @Transactional
    public void supprimerMembre(
            Integer espaceId,
            Integer membreId,
            RemoveMemberRequest request
    ) {

        // 1. Validate the request
        if (espaceId == null || espaceId <= 0
                || membreId == null || membreId <= 0
                || request == null
                || request.acteurId() == null
                || request.acteurId() <= 0) {

            throw new IllegalArgumentException(
                    "Les identifiants de l'espace, du membre et de l'acteur sont obligatoires."
            );
        }

        // 2. Find the workspace
        Espace espace = espaceRepository.findById(espaceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Espace introuvable : " + espaceId
                ));

        // 3. Verify that the workspace is active
        if (!Boolean.TRUE.equals(espace.getEstActive())) {
            throw new ConflictException(
                    "Impossible de supprimer un membre d'un espace inactif."
            );
        }

// Verify workspace membership
        if (!appartenanceService.estMembre(request.acteurId(), espaceId)) {
            throw new ForbiddenException(
                    "Vous n'appartenez pas à cet espace."
            );
        }

// Verify Scrum Master permission
        if (!appartenanceService.estScrumMaster(request.acteurId(), espaceId)) {
            throw new ForbiddenException(
                    "Seul un Scrum Master peut supprimer des membres."
            );
        }

        // 6. Verify the target member belongs to the workspace
        Appartenance membre = appartenanceRepository
                .findByUtilisateurIdAndEspaceId(
                        membreId,
                        espaceId
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ce membre n'appartient pas à cet espace."
                ));

        // 7. Prevent removing the Scrum Master
        if (membre.getRoleAttribue() == RoleEspace.SCRUM_MASTER) {
            throw new ConflictException(
                    "Le Scrum Master ne peut pas être supprimé de cet espace."
            );
        }

        // 8. Delete the membership, not the user account
        appartenanceRepository.delete(membre);
    }


    @Override
    @Transactional
    public void quitterEspace(
            Integer espaceId,
            LeaveWorkspaceRequest request
    ) {

        // 1. Validate request
        if (espaceId == null || espaceId <= 0
                || request == null
                || request.utilisateurId() == null
                || request.utilisateurId() <= 0) {

            throw new IllegalArgumentException(
                    "L'identifiant de l'espace et de l'utilisateur sont obligatoires."
            );
        }

        // 2. Verify that the workspace exists
        Espace espace = espaceRepository.findById(espaceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Espace introuvable : " + espaceId
                ));

        // 3. Verify that the user belongs to the workspace
        Appartenance appartenance = appartenanceRepository
                .findByUtilisateurIdAndEspaceId(
                        request.utilisateurId(),
                        espaceId
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vous n'appartenez pas à cet espace."
                ));

        // 4. Prevent the Scrum Master from leaving
        if (appartenance.getRoleAttribue() == RoleEspace.SCRUM_MASTER) {
            throw new ConflictException(
                    "Le Scrum Master ne peut pas quitter cet espace. "
                            + "Il doit d'abord transférer sa responsabilité."
            );
        }

        // 5. Remove the membership
        appartenanceRepository.delete(appartenance);
    }


    @Override
    @Transactional
    public void transfererScrumMaster(
            Integer espaceId,
            TransferScrumMasterRequest request
    ) {

        // 1. Validate request
        if (espaceId == null || espaceId <= 0
                || request == null
                || request.acteurId() == null
                || request.acteurId() <= 0
                || request.nouveauScrumMasterId() == null
                || request.nouveauScrumMasterId() <= 0) {

            throw new IllegalArgumentException(
                    "Les identifiants de l'espace et des utilisateurs sont obligatoires."
            );
        }

        // 2. Prevent self-transfer
        if (request.acteurId().equals(request.nouveauScrumMasterId())) {
            throw new ConflictException(
                    "Vous ne pouvez pas transférer le rôle Scrum Master à vous-même."
            );
        }

        // 3. Lock the workspace to serialize transfers
        Espace espace = entityManager.find(
                Espace.class,
                espaceId,
                LockModeType.PESSIMISTIC_WRITE
        );

        if (espace == null) {
            throw new ResourceNotFoundException(
                    "Espace introuvable : " + espaceId
            );
        }

        // 4. Verify workspace is active
        if (!Boolean.TRUE.equals(espace.getEstActive())) {
            throw new ConflictException(
                    "Impossible de transférer le Scrum Master d'un espace inactif."
            );
        }

        // 5. Find current Scrum Master membership
        Appartenance acteur = appartenanceRepository
                .findByUtilisateurIdAndEspaceId(
                        request.acteurId(),
                        espaceId
                )
                .orElseThrow(() -> new ForbiddenException(
                        "Vous n'appartenez pas à cet espace."
                ));

        if (acteur.getRoleAttribue() != RoleEspace.SCRUM_MASTER) {
            throw new ForbiddenException(
                    "Seul le Scrum Master actuel peut transférer sa responsabilité."
            );
        }

        // 6. Find the new Scrum Master
        Appartenance nouveauScrumMaster = appartenanceRepository
                .findByUtilisateurIdAndEspaceId(
                        request.nouveauScrumMasterId(),
                        espaceId
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le nouveau Scrum Master doit déjà appartenir à cet espace."
                ));

        // 7. Transfer responsibility
        acteur.setRoleAttribue(RoleEspace.MEMBRE);
        nouveauScrumMaster.setRoleAttribue(RoleEspace.SCRUM_MASTER);

        // 8. Save both memberships
        appartenanceRepository.save(acteur);
        appartenanceRepository.save(nouveauScrumMaster);
    }

}