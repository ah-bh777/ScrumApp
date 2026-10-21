
package com.ISICOD.ScrumApp.Services.DailyMeeting;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.*;
import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Entities.DailyContent;
import com.ISICOD.ScrumApp.Entities.DailyStoryReservation;
import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Entities.ParticipantSession;
import com.ISICOD.ScrumApp.Entities.Session;
import com.ISICOD.ScrumApp.Entities.Sprint;
import com.ISICOD.ScrumApp.Entities.SprintUserStory;
import com.ISICOD.ScrumApp.Entities.TypeSession;
import com.ISICOD.ScrumApp.Entities.Utilisateur;
import com.ISICOD.ScrumApp.Enums.DailyEventType;
import com.ISICOD.ScrumApp.Enums.RoleEspace;
import com.ISICOD.ScrumApp.Enums.RoleSession;
import com.ISICOD.ScrumApp.Enums.StatutSession;
import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import com.ISICOD.ScrumApp.Exceptions.BadRequestException;
import com.ISICOD.ScrumApp.Exceptions.ConflictException;
import com.ISICOD.ScrumApp.Exceptions.ForbiddenException;
import com.ISICOD.ScrumApp.Exceptions.ResourceNotFoundException;
import com.ISICOD.ScrumApp.Repositories.AppartenanceRepository;
import com.ISICOD.ScrumApp.Repositories.DailyContentRepository;
import com.ISICOD.ScrumApp.Repositories.EspaceRepository;
import com.ISICOD.ScrumApp.Repositories.ParticipantSessionRepository;
import com.ISICOD.ScrumApp.Repositories.SessionRepository;
import com.ISICOD.ScrumApp.Repositories.SprintRepository;
import com.ISICOD.ScrumApp.Repositories.SprintUserStoryRepository;
import com.ISICOD.ScrumApp.Repositories.TypeSessionRepository;
import com.ISICOD.ScrumApp.Repositories.UtilisateurRepository;
import com.ISICOD.ScrumApp.Services.Builders.DailyMeeting.DailyMeetingBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ISICOD.ScrumApp.Repositories.DailyStoryReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyServiceImpl implements DailyService {

    private final SessionRepository sessionRepository;
    private final EspaceRepository espaceRepository;
    private final SprintRepository sprintRepository;
    private final TypeSessionRepository typeSessionRepository;
    private final ParticipantSessionRepository participantSessionRepository;
    private final DailyContentRepository dailyContentRepository;
    private final SprintUserStoryRepository sprintUserStoryRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AppartenanceRepository appartenanceRepository;
    private final DailyMeetingBuilder dailyMeetingBuilder;
    private final DailyWebSocketPublisher dailyWebSocketPublisher;
    private final DailyStoryReservationRepository reservationRepository;


    // ============================================================
    // GET DAILY
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public DailyDTO getDaily(Integer sessionId) {

        Session session = getSession(sessionId);

        validateDailySession(session);

        /*
         * Only ACTIVE participants are returned to the frontend.
         *
         * We keep inactive ParticipantSession records in the database
         * because they may still be referenced by DailyContent.
         */
        List<ParticipantSession> participants =
                participantSessionRepository.findAll()
                        .stream()
                        .filter(p ->
                                p.getSession() != null
                                        && p.getSession().getId().equals(sessionId)
                                        && Boolean.TRUE.equals(p.getActif())
                        )
                        .toList();

        List<DailyContent> contents =
                dailyContentRepository.findBySessionId(sessionId);

        return dailyMeetingBuilder.buildDaily(
                session,
                participants,
                contents
        );
    }


    // ============================================================
    // GET ACTIVE DAILY
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public DailyDTO getActiveDaily(Integer espaceId) {

        Session session =
                sessionRepository
                        .findFirstByEspaceIdAndTypeSessionCodeAndStatusOrderByIdDesc(
                                espaceId,
                                TypeSessionCode.DAILY,
                                StatutSession.ACTIVE
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Aucune Daily active pour ce workspace."
                                )
                        );

        return getDaily(session.getId());
    }


    // ============================================================
    // GET STORIES OF A SPRINT
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<DailyStoryOptionDTO> getDailyStories(Integer sprintId) {

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Sprint introuvable : " + sprintId
                        )
                );

        return sprintUserStoryRepository
                .findBySprintId(sprint.getId())
                .stream()
                .map(dailyMeetingBuilder::buildStoryOption)
                .toList();
    }


    // ============================================================
    // CREATE DAILY
    // ============================================================

    @Override
    public DailyDTO createDaily(
            Integer espaceId,
            Integer sprintId,
            Integer utilisateurId,
            DailySessionRequestDTO request) {

        Espace espace = espaceRepository.findById(espaceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Workspace introuvable : " + espaceId
                        )
                );

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Sprint introuvable : " + sprintId
                        )
                );

        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Utilisateur introuvable : " + utilisateurId
                        )
                );


        /*
         * Vérifie que le sprint appartient bien au workspace.
         */
        if (sprint.getEspace() == null
                || !sprint.getEspace().getId().equals(espaceId)) {

            throw new BadRequestException(
                    "Le sprint n'appartient pas à ce workspace."
            );
        }


        /*
         * Vérifie que l'utilisateur appartient au workspace.
         */
        Appartenance appartenance =
                appartenanceRepository
                        .findByUtilisateurIdAndEspaceId(
                                utilisateurId,
                                espaceId
                        )
                        .orElseThrow(() ->
                                new ForbiddenException(
                                        "L'utilisateur n'est pas membre de ce workspace."
                                )
                        );


        /*
         * Seul le SCRUM_MASTER peut créer une Daily.
         */
        if (appartenance.getRoleAttribue()
                != RoleEspace.SCRUM_MASTER) {

            throw new ForbiddenException(
                    "Seul le SCRUM_MASTER peut créer une Daily."
            );
        }


        /*
         * Récupération du type DAILY.
         */
        TypeSession typeDaily =
                typeSessionRepository
                        .findByCode(TypeSessionCode.DAILY)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Le type de session DAILY n'existe pas."
                                )
                        );


        /*
         * Vérifie qu'il n'existe pas déjà
         * une Daily ACTIVE dans ce workspace.
         */
        boolean activeDailyExists =
                sessionRepository
                        .findFirstByEspaceIdAndTypeSessionCodeAndStatusOrderByIdDesc(
                                espaceId,
                                TypeSessionCode.DAILY,
                                StatutSession.ACTIVE
                        )
                        .isPresent();

        if (activeDailyExists) {

            throw new ConflictException(
                    "Une Daily est déjà active dans ce workspace."
            );
        }


        /*
         * Création de la session Daily.
         */
        Session session = Session.builder()
                .titre(request.getTitre())
                .commenceA(LocalDateTime.now())
                .status(StatutSession.ACTIVE)
                .creaA(LocalDateTime.now())
                .espace(espace)
                .sprint(sprint)
                .typeSession(typeDaily)
                .build();

        session = sessionRepository.save(session);


        /*
         * Le créateur devient automatiquement ANIMATEUR
         * et est actif dans la Daily.
         */
        ParticipantSession participant =
                ParticipantSession.builder()
                        .pseudo(
                                utilisateur.getPrenom()
                                        + " "
                                        + utilisateur.getNom()
                        )
                        .estInvite(false)
                        .actif(true)
                        .roleSession(RoleSession.ANIMATEUR)
                        .utilisateur(utilisateur)
                        .session(session)
                        .rejointA(LocalDateTime.now())
                        .build();

        participantSessionRepository.save(participant);


        /*
         * Notification WebSocket.
         */
        DailyWebSocketEventDTO event =
                DailyWebSocketEventDTO.builder()
                        .event(DailyEventType.DAILY_CREATED)
                        .sessionId(session.getId())
                        .session(
                                dailyMeetingBuilder.buildSession(session)
                        )
                        .build();

        dailyWebSocketPublisher.publishToWorkspace(
                espaceId,
                event
        );

        return getDaily(session.getId());
    }


    // ============================================================
    // JOIN DAILY
    // ============================================================

    @Override
    public DailyDTO joinDaily(
            Integer sessionId,
            ParticipantJoinRequestDTO request) {

        Session session = getSession(sessionId);

        validateDailySession(session);


        /*
         * Une Daily TERMINEE ne peut plus être rejointe.
         */
        if (session.getStatus() == StatutSession.TERMINEE) {

            throw new ConflictException(
                    "Cette Daily est déjà terminée."
            );
        }


        Utilisateur utilisateur =
                utilisateurRepository
                        .findById(request.getUtilisateurId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Utilisateur introuvable : "
                                                + request.getUtilisateurId()
                                )
                        );


        /*
         * Vérifie que l'utilisateur appartient au workspace
         * de la Daily.
         */
        Appartenance appartenance =
                appartenanceRepository
                        .findByUtilisateurIdAndEspaceId(
                                request.getUtilisateurId(),
                                session.getEspace().getId()
                        )
                        .orElseThrow(() ->
                                new ForbiddenException(
                                        "L'utilisateur n'est pas membre de ce workspace."
                                )
                        );


        /*
         * Par défaut, un utilisateur rejoint comme PARTICIPANT.
         */
        RoleSession role = request.getRoleSession();

        if (role == null) {
            role = RoleSession.PARTICIPANT;
        }


        /*
         * Seul un SCRUM_MASTER peut être ANIMATEUR.
         */
        if (role == RoleSession.ANIMATEUR
                && appartenance.getRoleAttribue()
                != RoleEspace.SCRUM_MASTER) {

            throw new ForbiddenException(
                    "Seul le SCRUM_MASTER peut être ANIMATEUR."
            );
        }


        /*
         * Si aucun pseudo n'est fourni,
         * on utilise prénom + nom.
         */
        String pseudo = request.getPseudo();

        if (pseudo == null || pseudo.isBlank()) {
            pseudo = utilisateur.getPrenom()
                    + " "
                    + utilisateur.getNom();
        }


        /*
         * Cherche un ancien ParticipantSession.
         *
         * Cas 1:
         * participant existe + actif=true
         * => déjà dans la Daily => 409
         *
         * Cas 2:
         * participant existe + actif=false
         * => il avait quitté => on le réactive
         *
         * Cas 3:
         * participant n'existe pas
         * => on crée un nouveau ParticipantSession
         */
        ParticipantSession participant =
                participantSessionRepository
                        .findBySessionIdAndUtilisateurId(
                                sessionId,
                                request.getUtilisateurId()
                        )
                        .orElse(null);


        if (participant != null) {

            /*
             * L'utilisateur est déjà actif.
             */
            if (Boolean.TRUE.equals(participant.getActif())) {

                throw new ConflictException(
                        "L'utilisateur participe déjà à cette Daily."
                );
            }


            /*
             * L'utilisateur avait quitté la Daily.
             * On réactive le même ParticipantSession.
             */
            participant.setActif(true);
            participant.setPseudo(pseudo);
            participant.setRoleSession(role);
            participant.setRejointA(LocalDateTime.now());
            participant.setQuitteA(null);

        } else {

            /*
             * Première participation à cette Daily.
             */
            participant =
                    ParticipantSession.builder()
                            .pseudo(pseudo)
                            .estInvite(false)
                            .actif(true)
                            .roleSession(role)
                            .utilisateur(utilisateur)
                            .session(session)
                            .rejointA(LocalDateTime.now())
                            .build();
        }


        participantSessionRepository.save(participant);


        /*
         * Notification WebSocket.
         */
        DailyWebSocketEventDTO event =
                DailyWebSocketEventDTO.builder()
                        .event(DailyEventType.PARTICIPANT_JOINED)
                        .sessionId(sessionId)
                        .participant(
                                dailyMeetingBuilder.buildParticipant(
                                        participant,
                                        List.of()
                                )
                        )
                        .build();

        dailyWebSocketPublisher.publish(
                sessionId,
                event
        );

        return getDaily(sessionId);
    }


    // ============================================================
    // LEAVE DAILY
    // ============================================================

    @Override
    public DailyDTO leaveDaily(
            Integer sessionId,
            Integer participantId) {

        Session session = getSession(sessionId);

        validateDailySession(session);

        /*
         * On ne peut quitter qu'une Daily encore active.
         */
        validateActiveDaily(session);


        ParticipantSession participant =
                participantSessionRepository
                        .findById(participantId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Participant introuvable : "
                                                + participantId
                                )
                        );


        /*
         * Vérifie que le participant appartient
         * bien à cette Daily.
         */
        if (participant.getSession() == null
                || !participant.getSession().getId().equals(sessionId)) {

            throw new BadRequestException(
                    "Ce participant n'appartient pas à cette Daily."
            );
        }


        /*
         * Le participant est déjà parti.
         */
        if (!Boolean.TRUE.equals(participant.getActif())) {

            throw new ConflictException(
                    "Le participant a déjà quitté cette Daily."
            );
        }


        /*
         * IMPORTANT:
         *
         * On ne supprime PAS ParticipantSession.
         *
         * On le désactive seulement afin de conserver
         * les contenus Daily et l'historique.
         */
        participant.setActif(false);
        participant.setQuitteA(LocalDateTime.now());

        participantSessionRepository.save(participant);


        /*
         * Notification WebSocket.
         */
        DailyWebSocketEventDTO event =
                DailyWebSocketEventDTO.builder()
                        .event(DailyEventType.PARTICIPANT_LEFT)
                        .sessionId(sessionId)
                        .participant(
                                dailyMeetingBuilder.buildParticipant(
                                        participant,
                                        List.of()
                                )
                        )
                        .build();

        dailyWebSocketPublisher.publish(
                sessionId,
                event
        );

        return getDaily(sessionId);
    }


    // ============================================================
    // ADD CONTENT
    // ============================================================

    @Override
    public DailyDTO addContent(Integer sessionId, DailyContentRequestDTO request) {
        Session session = getSession(sessionId);
        validateActiveDaily(session);

        if (request == null || request.getUtilisateurId() == null
                || request.getParticipantSessionId() == null
                || request.getSprintUserStoryId() == null
                || request.getType() == null
                || request.getDescription() == null
                || request.getDescription().isBlank()) {
            throw new BadRequestException(
                    "utilisateurId, participantSessionId, sprintUserStoryId, type et description sont obligatoires."
            );
        }

        ParticipantSession participant = lockWritingParticipant(
                sessionId, request.getUtilisateurId(), request.getParticipantSessionId());
        SprintUserStory story = lockAndValidateStory(session, request.getSprintUserStoryId());
        requireOwnedActiveReservation(sessionId, story.getId(), participant);

        DailyContent content = DailyContent.builder()
                .contenu(request.getDescription().trim())
                .typeContenu(request.getType())
                .session(session)
                .participantSession(participant)
                .sprintUserStory(story)
                .build();

        dailyContentRepository.saveAndFlush(content);
        dailyWebSocketPublisher.publish(sessionId, DailyWebSocketEventDTO.builder()
                .event(DailyEventType.CONTENT_ADDED)
                .sessionId(sessionId)
                .content(dailyMeetingBuilder.buildContent(content))
                .build());
        return getDaily(sessionId);
    }

    // ============================================================
    // UPDATE CONTENT
    // ============================================================

    @Override
    public DailyDTO updateContent(
            Integer sessionId,
            Integer dailyContentId,
            DailyContentUpdateDTO request) {



        Session session = getSession(sessionId);
        validateActiveDaily(session);

        if (request == null || request.getUtilisateurId() == null) {
            throw new BadRequestException("utilisateurId est obligatoire.");
        }

        DailyContent content = dailyContentRepository.findById(dailyContentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contenu Daily introuvable : " + dailyContentId));

        if (!content.getSession().getId().equals(sessionId)) {
            throw new BadRequestException(
                    "Ce contenu n'appartient pas à cette Daily.");
        }

        Integer participantId = content.getParticipantSession().getId();

        // Editing an existing contribution is allowed after submission.
        ParticipantSession participant = lockContributionOwner(
                sessionId,
                request.getUtilisateurId(),
                participantId
        );

        SprintUserStory oldStory = content.getSprintUserStory();
        Integer oldStoryId = oldStory == null ? null : oldStory.getId();

        boolean changingStory = Boolean.TRUE.equals(request.getUpdateStory())
                || request.getSprintUserStoryId() != null;

        Integer targetStoryId = changingStory
                ? request.getSprintUserStoryId()
                : oldStoryId;

        List<Integer> storyIds = java.util.stream.Stream
                .of(oldStoryId, targetStoryId)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .toList();

        java.util.Map<Integer, SprintUserStory> lockedStories =
                new java.util.HashMap<>();

        for (Integer storyId : storyIds) {
            lockedStories.put(
                    storyId,
                    lockAndValidateStory(session, storyId)
            );
        }

        // Only require reservation for a selected target Story.
        // Preserve existing reservation ownership rules.
        if (targetStoryId != null) {
            requireOwnedActiveReservation(
                    sessionId, targetStoryId, participant);
        }

        content.setSprintUserStory(
                targetStoryId == null
                        ? null
                        : lockedStories.get(targetStoryId)
        );

        if (request.getType() != null) {
            content.setTypeContenu(request.getType());
        }

        if (request.getDescription() != null) {
            if (request.getDescription().isBlank()) {
                throw new BadRequestException(
                        "La description ne peut pas être vide.");
            }

            content.setContenu(request.getDescription().trim());
        }

        dailyContentRepository.saveAndFlush(content);

        dailyWebSocketPublisher.publish(
                sessionId,
                DailyWebSocketEventDTO.builder()
                        .event(DailyEventType.CONTENT_UPDATED)
                        .sessionId(sessionId)
                        .content(dailyMeetingBuilder.buildContent(content))
                        .build()
        );

        return getDaily(sessionId);
    }

    // ============================================================
    // DELETE CONTENT
    // ============================================================


    @Override
    public void deleteContent(
            Integer sessionId,
            Integer dailyContentId,
            Integer utilisateurId) {

        // 1. Verify the Daily exists and is active.
        Session session = getSession(sessionId);
        validateActiveDaily(session);

        if (utilisateurId == null) {
            throw new BadRequestException(
                    "utilisateurId est obligatoire.");
        }

        // 2. Find the contribution.
        DailyContent content = dailyContentRepository.findById(dailyContentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contenu Daily introuvable : " + dailyContentId));

        // 3. Ensure it belongs to the requested Daily.
        if (content.getSession() == null
                || !Objects.equals(
                content.getSession().getId(),
                sessionId)) {

            throw new BadRequestException(
                    "Ce contenu n'appartient pas à cette Daily.");
        }

        // 4. Verify ownership without blocking submitted participants.
        ParticipantSession participant = lockContributionOwner(
                sessionId,
                utilisateurId,
                content.getParticipantSession().getId()
        );

        // IMPORTANT:
        // Do not reject participant.getSoumise() == true.
        // Submitted participants can delete existing contributions.

        // 7. Handle optional User Story safely.
        SprintUserStory story = content.getSprintUserStory();

        Integer storyId = story != null
                ? story.getId()
                : null;

        // 8. Validate the Story and reservation only when present.
        if (storyId != null) {

            lockAndValidateStory(session, storyId);

            requireOwnedActiveReservation(
                    sessionId,
                    storyId,
                    participant
            );
        }

        // 9. Build the DTO before deleting the entity.
        DailyContentDTO deletedContent =
                dailyMeetingBuilder.buildContent(content);

        // 10. Delete the existing contribution.
        dailyContentRepository.delete(content);
        dailyContentRepository.flush();

        // 11. Notify connected participants.
        dailyWebSocketPublisher.publish(
                sessionId,
                DailyWebSocketEventDTO.builder()
                        .event(DailyEventType.CONTENT_DELETED)
                        .sessionId(sessionId)
                        .content(deletedContent)
                        .build()
        );
    }


    // ============================================================
    // RESET MY SUBMISSION
    // ============================================================

    @Override
    public DailyDTO resetMySubmission(Integer sessionId, Integer authenticatedUtilisateurId) {
        Session session = getSession(sessionId);
        validateActiveDaily(session);

        if (authenticatedUtilisateurId == null) {
            throw new ForbiddenException("Utilisateur authentifié requis.");
        }

        ParticipantSession participant = participantSessionRepository
                .findBySessionIdAndUtilisateurId(sessionId, authenticatedUtilisateurId)
                .orElseThrow(() -> new ForbiddenException(
                        "Vous ne participez pas à cette Daily."));

        // Use the existing locking query so reset and submit serialize on the participant.
        participant = lockContributionOwner(
                sessionId, authenticatedUtilisateurId, participant.getId());

        if (!Boolean.TRUE.equals(participant.getSoumise())) {
            throw new ConflictException("Vous n'avez pas de soumission à réinitialiser.");
        }

        final Integer ownerParticipantId = participant.getId();

        List<DailyContent> ownedContents = dailyContentRepository
                .findBySessionId(sessionId)
                .stream()
                .filter(content -> content.getParticipantSession() != null
                        && Objects.equals(content.getParticipantSession().getId(), ownerParticipantId))
                .toList();

        // Snapshot DTOs before deleting JPA entities.
        List<DailyContentDTO> removedContents = ownedContents.stream()
                .map(dailyMeetingBuilder::buildContent)
                .toList();

        // Only release reservations owned by this participant in this session.
        List<DailyStoryReservation> ownedReservations = reservationRepository
                .findAll()
                .stream()
                .filter(reservation -> reservation.getSession() != null
                        && Objects.equals(reservation.getSession().getId(), sessionId)
                        && reservation.getParticipantSession() != null
                        && Objects.equals(reservation.getParticipantSession().getId(), ownerParticipantId))
                .toList();

        dailyContentRepository.deleteAll(ownedContents);
        dailyContentRepository.flush();

        reservationRepository.deleteAll(ownedReservations);
        reservationRepository.flush();

        participant.setSoumise(false);
        participant.setSoumise_a(null);
        participantSessionRepository.saveAndFlush(participant);

        // A single event tells every client to clear this participant's submitted state.
        // CONTENT_DELETED events preserve compatibility with existing contribution feeds.
        for (DailyContentDTO removed : removedContents) {
            dailyWebSocketPublisher.publish(sessionId, DailyWebSocketEventDTO.builder()
                    .event(DailyEventType.CONTENT_DELETED)
                    .sessionId(sessionId)
                    .content(removed)
                    .build());
        }

        dailyWebSocketPublisher.publish(sessionId, DailyWebSocketEventDTO.builder()
                .event(DailyEventType.SUBMISSION_RESET)
                .sessionId(sessionId)
                .participant(dailyMeetingBuilder.buildParticipant(participant, List.of()))
                .build());

        return getDaily(sessionId);
    }

    // ============================================================
    // UPDATE SESSION STATUS
    // ============================================================

    @Override
    public DailyDTO updateSessionStatus(
            Integer sessionId,
            Integer utilisateurId,
            SessionStatusUpdateDTO request) {

        Session session = getSession(sessionId);

        validateDailySession(session);


        if (request.getStatut() == null) {

            throw new BadRequestException(
                    "Le statut est obligatoire."
            );
        }


        /*
         * TERMINEE est un cas particulier.
         *
         * Le Scrum Master doit pouvoir terminer la Daily
         * même s'il a précédemment quitté la réunion.
         *
         * On vérifie donc son rôle WORKSPACE,
         * et non pas son ParticipantSession actif.
         */
        if (request.getStatut() == StatutSession.TERMINEE) {

            Appartenance appartenance =
                    appartenanceRepository
                            .findByUtilisateurIdAndEspaceId(
                                    utilisateurId,
                                    session.getEspace().getId()
                            )
                            .orElseThrow(() ->
                                    new ForbiddenException(
                                            "L'utilisateur n'est pas membre de ce workspace."
                                    )
                            );

            if (appartenance.getRoleAttribue()
                    != RoleEspace.SCRUM_MASTER) {

                throw new ForbiddenException(
                        "Seul le SCRUM_MASTER peut terminer la Daily."
                );
            }

        } else {

            /*
             * Pour les autres changements de statut,
             * on conserve la règle existante:
             * l'utilisateur doit être un ANIMATEUR actif.
             */
            ParticipantSession participant =
                    participantSessionRepository
                            .findBySessionIdAndUtilisateurId(
                                    sessionId,
                                    utilisateurId
                            )
                            .orElseThrow(() ->
                                    new ForbiddenException(
                                            "L'utilisateur ne participe pas à cette Daily."
                                    )
                            );

            validateActiveParticipant(participant);


            if (participant.getRoleSession()
                    != RoleSession.ANIMATEUR) {

                throw new ForbiddenException(
                        "Seul l'ANIMATEUR peut modifier le statut de la Daily."
                );
            }
        }


        session.setStatus(request.getStatut());


        /*
         * Si la Daily est terminée,
         * on enregistre la date de fin.
         */
        if (request.getStatut()
                == StatutSession.TERMINEE) {

            session.setTermineA(LocalDateTime.now());
        }

        sessionRepository.save(session);


        DailyWebSocketEventDTO event =
                DailyWebSocketEventDTO.builder()
                        .event(DailyEventType.SESSION_STATUS_CHANGED)
                        .sessionId(sessionId)
                        .session(
                                dailyMeetingBuilder.buildSession(session)
                        )
                        .build();

        dailyWebSocketPublisher.publish(
                sessionId,
                event
        );

        return getDaily(sessionId);
    }


    // ============================================================
    // PRIVATE METHODS
    // ============================================================

    private Session getSession(Integer sessionId) {

        return sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Session introuvable : " + sessionId
                        )
                );
    }


    private void validateDailySession(Session session) {

        if (session.getTypeSession() == null
                || session.getTypeSession().getCode()
                != TypeSessionCode.DAILY) {

            throw new BadRequestException(
                    "Cette session n'est pas une Daily."
            );
        }
    }


    private void validateActiveDaily(Session session) {

        validateDailySession(session);

        if (session.getStatus()
                != StatutSession.ACTIVE) {

            throw new BadRequestException(
                    "La Daily n'est pas active."
            );
        }
    }


    private void validateActiveParticipant(
            ParticipantSession participant) {

        if (!Boolean.TRUE.equals(participant.getActif())) {

            throw new ForbiddenException(
                    "Le participant a quitté cette Daily."
            );
        }
    }


    private void validateCanWrite(
            ParticipantSession participant) {

        if (participant.getRoleSession()
                == RoleSession.OBSERVATEUR) {

            throw new ForbiddenException(
                    "Un observateur ne peut pas modifier le contenu de la Daily."
            );
        }
    }


    // ============================================================
    // RESERVATION OWNERSHIP FOR DRAFT ENDPOINTS
    // ============================================================

    private ParticipantSession lockContributionOwner(
            Integer sessionId,
            Integer utilisateurId,
            Integer participantId) {

        ParticipantSession participant =
                participantSessionRepository
                        .findForSubmission(participantId, sessionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Participant introuvable dans cette Daily."
                                )
                        );

        if (participant.getUtilisateur() == null
                || !Objects.equals(
                participant.getUtilisateur().getId(),
                utilisateurId)) {

            throw new ForbiddenException(
                    "Ce participant ne correspond pas à l'utilisateur."
            );
        }

        validateActiveParticipant(participant);
        validateCanWrite(participant);

        return participant;
    }

    private ParticipantSession lockWritingParticipant(
            Integer sessionId, Integer utilisateurId, Integer participantId) {
        ParticipantSession participant = participantSessionRepository
                .findForSubmission(participantId, sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Participant introuvable dans cette Daily."));

        if (participant.getUtilisateur() == null
                || !Objects.equals(participant.getUtilisateur().getId(), utilisateurId)) {
            throw new ForbiddenException("Ce participant ne correspond pas à l'utilisateur.");
        }
        validateActiveParticipant(participant);
        validateCanWrite(participant);
        if (Boolean.TRUE.equals(participant.getSoumise())) {
            throw new ConflictException("Vous avez déjà finalisé votre contribution.");
        }
        return participant;
    }

    private SprintUserStory lockAndValidateStory(Session session, Integer storyId) {
        SprintUserStory story = sprintUserStoryRepository.findByIdForReservation(storyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User Story introuvable : " + storyId));
        if (session.getSprint() == null || story.getSprint() == null
                || !Objects.equals(story.getSprint().getId(), session.getSprint().getId())) {
            throw new BadRequestException(
                    "Cette User Story n'appartient pas au sprint de cette Daily.");
        }
        return story;
    }

    private void requireOwnedActiveReservation(
            Integer sessionId, Integer storyId, ParticipantSession participant) {
        DailyStoryReservation reservation = reservationRepository
                .findBySessionIdAndSprintUserStoryId(sessionId, storyId)
                .orElseThrow(() -> new ConflictException(
                        "Vous devez réserver la User Story " + storyId + " avant de la modifier."));
        if (!Objects.equals(reservation.getParticipantSession().getId(), participant.getId())) {
            throw new ConflictException(
                    "La User Story " + storyId + " est réservée par un autre participant.");
        }
        if (!reservation.getExpiresAt().isAfter(LocalDateTime.now())) {
            throw new ConflictException(
                    "La réservation de la User Story " + storyId + " a expiré.");
        }
    }

    // ============================================================
    // FINALIZE DAILY SUBMISSION
    // ============================================================


    @Override
    public DailyDTO submitDaily(
            Integer sessionId,
            DailySubmissionRequestDTO request) {

        Session session = getSession(sessionId);
        validateActiveDaily(session);

        if (request == null
                || request.getUtilisateurId() == null
                || request.getParticipantSessionId() == null) {

            throw new BadRequestException(
                    "utilisateurId et participantSessionId sont obligatoires."
            );
        }

        if (request.getContents() == null
                || request.getContents().isEmpty()) {

            throw new BadRequestException(
                    "La soumission doit contenir au moins une contribution."
            );
        }

        ParticipantSession participant =
                participantSessionRepository
                        .findForSubmission(
                                request.getParticipantSessionId(),
                                sessionId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Participant introuvable dans cette Daily."
                                )
                        );

        if (participant.getUtilisateur() == null
                || !participant.getUtilisateur().getId()
                .equals(request.getUtilisateurId())) {

            throw new ForbiddenException(
                    "Ce participant ne correspond pas à l'utilisateur."
            );
        }

        validateActiveParticipant(participant);
        validateCanWrite(participant);

        if (Boolean.TRUE.equals(participant.getSoumise())) {
            throw new ConflictException(
                    "Vous avez déjà soumis votre contribution pour cette Daily."
            );
        }

        // 2. Validate the complete request before writing.
        for (DailySubmissionContentDTO item : request.getContents()) {

            if (item == null
                    || item.getType() == null
                    || item.getDescription() == null
                    || item.getDescription().isBlank()) {

                throw new BadRequestException(
                        "Chaque contribution doit avoir un type et une description."
                );
            }
        }

        // 3. Lock all selected Stories in a consistent order.
        // Multiple contributions may reference the same Story.
        List<Integer> storyIds = request.getContents()
                .stream()
                .map(DailySubmissionContentDTO::getSprintUserStoryId)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .toList();

        java.util.Map<Integer, SprintUserStory> stories =
                new java.util.HashMap<>();

        for (Integer storyId : storyIds) {

            SprintUserStory story =
                    sprintUserStoryRepository
                            .findByIdForReservation(storyId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "User Story introuvable : " + storyId
                                    )
                            );

            if (session.getSprint() == null
                    || story.getSprint() == null
                    || !story.getSprint().getId()
                    .equals(session.getSprint().getId())) {

                throw new BadRequestException(
                        "Une User Story n'appartient pas au sprint de cette Daily."
                );
            }

            // 4. Verify reservation ownership.
            var reservation = reservationRepository
                    .findBySessionIdAndSprintUserStoryId(
                            sessionId,
                            storyId
                    )
                    .orElseThrow(() ->
                            new ConflictException(
                                    "Vous devez réserver la User Story "
                                            + storyId
                                            + " avant de la soumettre."
                            )
                    );

            if (!reservation.getParticipantSession().getId()
                    .equals(participant.getId())) {

                throw new ConflictException(
                        "La User Story " + storyId
                                + " est réservée par un autre participant."
                );
            }

            // The reservation may have expired.
            if (!reservation.getExpiresAt()
                    .isAfter(LocalDateTime.now())) {

                throw new ConflictException(
                        "La réservation de la User Story "
                                + storyId
                                + " a expiré. Veuillez la réserver à nouveau."
                );
            }

            stories.put(storyId, story);
        }

        // 5. Build contributions after all checks succeed.
        List<DailyContent> contents = new java.util.ArrayList<>();

        for (DailySubmissionContentDTO item : request.getContents()) {

            DailyContent content = DailyContent.builder()
                    .contenu(item.getDescription().trim())
                    .typeContenu(item.getType())
                    .session(session)
                    .participantSession(participant)
                    .sprintUserStory(
                            stories.get(item.getSprintUserStoryId())
                    )
                    .build();

            contents.add(content);
        }

        // 6. Save everything atomically.
        List<DailyContent> savedContents =
                dailyContentRepository.saveAllAndFlush(contents);

        participant.setSoumise(true);
        participant.setSoumise_a(LocalDateTime.now());

        participantSessionRepository.saveAndFlush(participant);

        // 7. Broadcast contributions.
        for (DailyContent content : savedContents) {

            DailyWebSocketEventDTO contentEvent =
                    DailyWebSocketEventDTO.builder()
                            .event(DailyEventType.CONTENT_ADDED)
                            .sessionId(sessionId)
                            .content(
                                    dailyMeetingBuilder.buildContent(content)
                            )
                            .build();

            dailyWebSocketPublisher.publish(
                    sessionId,
                    contentEvent
            );
        }

        // 8. Broadcast submission completion.
        DailyWebSocketEventDTO submissionEvent =
                DailyWebSocketEventDTO.builder()
                        .event(DailyEventType.SUBMISSION_COMPLETED)
                        .sessionId(sessionId)
                        .participant(
                                dailyMeetingBuilder.buildParticipant(
                                        participant,
                                        savedContents
                                )
                        )
                        .build();

        dailyWebSocketPublisher.publish(
                sessionId,
                submissionEvent
        );

        return getDaily(sessionId);
    }


}
