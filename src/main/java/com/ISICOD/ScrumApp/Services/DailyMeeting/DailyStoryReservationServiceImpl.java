
package com.ISICOD.ScrumApp.Services.DailyMeeting;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.*;
import com.ISICOD.ScrumApp.Entities.*;
import com.ISICOD.ScrumApp.Enums.*;
import com.ISICOD.ScrumApp.Exceptions.*;
import com.ISICOD.ScrumApp.Repositories.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyStoryReservationServiceImpl
        implements DailyStoryReservationService {

    private final DailyStoryReservationRepository reservationRepository;
    private final SessionRepository sessionRepository;
    private final ParticipantSessionRepository participantSessionRepository;
    private final SprintUserStoryRepository sprintUserStoryRepository;
    private final DailyWebSocketPublisher dailyWebSocketPublisher;

    // Reservation lasts one hour unless the contribution is submitted.
    private static final int RESERVATION_MINUTES = 60;

    // ============================================================
    // RESERVE STORY
    // ============================================================

    @Override
    public DailyStoryReservationDTO reserveStory(
            Integer sessionId,
            DailyStoryReservationRequestDTO request) {

        validateRequest(request);

        Session session = getActiveDaily(sessionId);

        ParticipantSession participant =
                getValidParticipant(sessionId, request);

        // Locks the Story row to protect simultaneous reservations.
        SprintUserStory story =
                getValidStory(session, request.getSprintUserStoryId());

        LocalDateTime now = LocalDateTime.now();

        DailyStoryReservation existing =
                reservationRepository
                        .findBySessionIdAndSprintUserStoryId(
                                sessionId,
                                story.getId()
                        )
                        .orElse(null);

        if (existing != null) {

            // A submitted Story remains unavailable for this Daily.
            validateReservationNotSubmitted(existing);

            // Same participant already owns an active reservation.
            if (existing.getExpiresAt().isAfter(now)
                    && existing.getParticipantSession().getId()
                    .equals(participant.getId())) {

                return toDTO(existing);
            }

            // Another participant owns an active reservation.
            if (existing.getExpiresAt().isAfter(now)) {

                throw new ConflictException(
                        "Cette User Story est déjà réservée par un autre participant."
                );
            }

            // Previous reservation expired.
            // Reassign the existing row while holding the Story lock.
            existing.setParticipantSession(participant);
            existing.setReservedAt(now);
            existing.setExpiresAt(
                    now.plusMinutes(RESERVATION_MINUTES)
            );

            DailyStoryReservation saved =
                    reservationRepository.saveAndFlush(existing);

            DailyStoryReservationDTO dto = toDTO(saved);

            publishReservationEvent(
                    sessionId,
                    DailyEventType.STORY_RESERVED,
                    dto
            );

            return dto;
        }

        DailyStoryReservation reservation =
                DailyStoryReservation.builder()
                        .session(session)
                        .sprintUserStory(story)
                        .participantSession(participant)
                        .reservedAt(now)
                        .expiresAt(
                                now.plusMinutes(RESERVATION_MINUTES)
                        )
                        .build();

        DailyStoryReservation saved =
                reservationRepository.saveAndFlush(reservation);

        DailyStoryReservationDTO dto = toDTO(saved);

        publishReservationEvent(
                sessionId,
                DailyEventType.STORY_RESERVED,
                dto
        );

        return dto;
    }

    // ============================================================
    // RELEASE STORY
    // ============================================================

    @Override
    public void releaseStory(
            Integer sessionId,
            DailyStoryReservationRequestDTO request) {

        validateRequest(request);

        Session session = getActiveDaily(sessionId);

        ParticipantSession participant =
                getValidParticipant(sessionId, request);

        // Use the same Story lock as reserveStory().
        getValidStory(session, request.getSprintUserStoryId());

        DailyStoryReservation reservation =
                reservationRepository
                        .findBySessionIdAndSprintUserStoryId(
                                sessionId,
                                request.getSprintUserStoryId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Aucune réservation trouvée pour cette User Story."
                                )
                        );

        if (!reservation.getParticipantSession().getId()
                .equals(participant.getId())) {

            throw new ForbiddenException(
                    "Vous ne pouvez libérer que vos propres réservations."
            );
        }

        // A submitted contribution cannot be released.
        validateReservationNotSubmitted(reservation);

        DailyStoryReservationDTO dto = toDTO(reservation);

        reservationRepository.delete(reservation);
        reservationRepository.flush();

        publishReservationEvent(
                sessionId,
                DailyEventType.STORY_RELEASED,
                dto
        );
    }

    // ============================================================
    // GET RESERVATIONS
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<DailyStoryReservationDTO> getReservations(
            Integer sessionId) {

        getActiveDaily(sessionId);

        LocalDateTime now = LocalDateTime.now();

        return reservationRepository.findBySessionId(sessionId)
                .stream()
                .filter(reservation ->
                        Boolean.TRUE.equals(
                                reservation.getParticipantSession().getSoumise()
                        )
                                || reservation.getExpiresAt().isAfter(now)
                )
                .map(this::toDTO)
                .toList();
    }

    // ============================================================
    // VALIDATE SUBMISSION STATE
    // ============================================================

    private void validateReservationNotSubmitted(
            DailyStoryReservation reservation) {

        if (Boolean.TRUE.equals(
                reservation.getParticipantSession().getSoumise())) {

            throw new ConflictException(
                    "Cette User Story appartient à une contribution déjà soumise."
            );
        }
    }

    // ============================================================
    // VALIDATE REQUEST
    // ============================================================

    private void validateRequest(
            DailyStoryReservationRequestDTO request) {

        if (request == null
                || request.getUtilisateurId() == null
                || request.getParticipantSessionId() == null
                || request.getSprintUserStoryId() == null) {

            throw new BadRequestException(
                    "utilisateurId, participantSessionId et sprintUserStoryId sont obligatoires."
            );
        }
    }

    // ============================================================
    // VALIDATE ACTIVE DAILY
    // ============================================================

    private Session getActiveDaily(Integer sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Daily introuvable : " + sessionId
                        )
                );

        if (session.getTypeSession() == null
                || session.getTypeSession().getCode()
                != TypeSessionCode.DAILY) {

            throw new BadRequestException(
                    "Cette session n'est pas une Daily."
            );
        }

        if (session.getStatus() != StatutSession.ACTIVE) {

            throw new ConflictException(
                    "La Daily n'est plus active."
            );
        }

        return session;
    }

    // ============================================================
    // VALIDATE PARTICIPANT
    // ============================================================

    private ParticipantSession getValidParticipant(
            Integer sessionId,
            DailyStoryReservationRequestDTO request) {

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

        if (!Boolean.TRUE.equals(participant.getActif())) {

            throw new ForbiddenException(
                    "Le participant n'est pas actif dans cette Daily."
            );
        }

        if (participant.getRoleSession() == RoleSession.OBSERVATEUR) {

            throw new ForbiddenException(
                    "Un observateur ne peut pas réserver de User Story."
            );
        }

        if (Boolean.TRUE.equals(participant.getSoumise())) {

            throw new ConflictException(
                    "Vous avez déjà finalisé votre contribution."
            );
        }

        return participant;
    }

    // ============================================================
    // VALIDATE AND LOCK STORY
    // ============================================================

    private SprintUserStory getValidStory(
            Session session,
            Integer sprintUserStoryId) {

        SprintUserStory story =
                sprintUserStoryRepository
                        .findByIdForReservation(sprintUserStoryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User Story introuvable : "
                                                + sprintUserStoryId
                                )
                        );

        if (session.getSprint() == null
                || story.getSprint() == null
                || !story.getSprint().getId()
                .equals(session.getSprint().getId())) {

            throw new BadRequestException(
                    "Cette User Story n'appartient pas au sprint de la Daily."
            );
        }

        return story;
    }

    // ============================================================
    // MAPPING
    // ============================================================

    private DailyStoryReservationDTO toDTO(
            DailyStoryReservation reservation) {

        return DailyStoryReservationDTO.builder()
                .reservationId(reservation.getId())
                .sessionId(reservation.getSession().getId())
                .sprintUserStoryId(
                        reservation.getSprintUserStory().getId()
                )
                .participantSessionId(
                        reservation.getParticipantSession().getId()
                )
                .utilisateurId(
                        reservation.getParticipantSession()
                                .getUtilisateur() != null
                                ? reservation.getParticipantSession()
                                .getUtilisateur().getId()
                                : null
                )
                .reservedAt(reservation.getReservedAt())
                .expiresAt(reservation.getExpiresAt())
                .build();
    }

    // ============================================================
    // WEBSOCKET
    // ============================================================

    private void publishReservationEvent(
            Integer sessionId,
            DailyEventType eventType,
            DailyStoryReservationDTO reservationDTO) {

        DailyWebSocketEventDTO event =
                DailyWebSocketEventDTO.builder()
                        .event(eventType)
                        .sessionId(sessionId)
                        .reservation(reservationDTO)
                        .build();

        dailyWebSocketPublisher.publish(sessionId, event);
    }
}
