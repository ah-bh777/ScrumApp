package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.Profile.*;
import com.ISICOD.ScrumApp.Entities.*;
import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import com.ISICOD.ScrumApp.Services.Builders.ProfileBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileBuilderImpl implements ProfileBuilder {

    @Override
    public ProfileDTO build(
            Utilisateur utilisateur,
            List<Appartenance> appartenances,
            List<Invitation> invitations,
            List<ActionItem> actionItems,
            List<ParticipantSession> participations,
            List<Notification> notifications
    ) {

        ProfileUserDTO user = buildUser(utilisateur);

        List<ProfileWorkspaceDTO> workspaces =
                appartenances.stream()
                        .map(this::buildWorkspace)
                        .toList();

        List<ProfileInvitationDTO> invitationDTOs =
                invitations.stream()
                        .map(this::buildInvitation)
                        .toList();

        List<ProfileActionItemDTO> actionItemDTOs =
                actionItems.stream()
                        .map(this::buildActionItem)
                        .toList();

        List<ProfileSessionDTO> sessions =
                participations.stream()
                        .map(this::buildSession)
                        .toList();

        List<ProfileNotificationDTO> notificationDTOs =
                notifications.stream()
                        .map(this::buildNotification)
                        .toList();

        return ProfileDTO.builder()
                .utilisateur(user)
                .appartenances(workspaces)
                .invitations(invitationDTOs)
                .actionItems(actionItemDTOs)
                .sessions(sessions)
                .notifications(notificationDTOs)
                .build();
    }


    // ============================================================
    // USER
    // ============================================================

    private ProfileUserDTO buildUser(Utilisateur utilisateur) {

        return ProfileUserDTO.builder()
                .utilisateurId(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .build();
    }


    // ============================================================
    // WORKSPACE / APPARTENANCE
    // ============================================================

    private ProfileWorkspaceDTO buildWorkspace(
            Appartenance appartenance
    ) {

        Espace espace = appartenance.getEspace();

        return ProfileWorkspaceDTO.builder()
                .espaceId(espace.getId())
                .workspaceName(espace.getNom())
                .role(appartenance.getRoleAttribue())
                .rejointA(appartenance.getRejointA())
                .estActive(espace.getEstActive())
                .build();
    }


    // ============================================================
    // INVITATION
    // ============================================================

    private ProfileInvitationDTO buildInvitation(
            Invitation invitation
    ) {

        Espace espace = invitation.getEspace();

        return ProfileInvitationDTO.builder()
                .invitationId(invitation.getId())
                .espaceId(
                        espace != null
                                ? espace.getId()
                                : null
                )
                .workspaceName(
                        espace != null
                                ? espace.getNom()
                                : null
                )
                .role(invitation.getRole())
                .statut(invitation.getStatut())
                .expireA(invitation.getExpireA())
                .estValide(invitation.getEstValide())
                .build();
    }


    // ============================================================
    // ACTION ITEM
    // ============================================================

    private ProfileActionItemDTO buildActionItem(
            ActionItem actionItem
    ) {

        Espace espace = actionItem.getEspace();
        Session session = actionItem.getSession();

        Sprint sprint = session != null
                ? session.getSprint()
                : null;

        return ProfileActionItemDTO.builder()
                .actionItemId(actionItem.getId())
                .titre(actionItem.getTitre())
                .description(actionItem.getDescription())
                .statut(actionItem.getStatus())

                .workspaceId(
                        espace != null
                                ? espace.getId()
                                : null
                )

                .workspaceName(
                        espace != null
                                ? espace.getNom()
                                : null
                )

                .sprintId(
                        sprint != null
                                ? sprint.getId()
                                : null
                )

                .sprintTitre(
                        sprint != null
                                ? sprint.getTitre()
                                : null
                )

                .sessionId(
                        session != null
                                ? session.getId()
                                : null
                )

                .creeA(actionItem.getCreeA())
                .echeance(actionItem.getEcheance())

                /*
                 * Your current ActionItem entity does not contain
                 * a termineA field.
                 *
                 * Therefore we cannot populate it from the entity.
                 */
                .termineA(null)

                .build();
    }


    // ============================================================
    // SESSION PARTICIPATION
    // ============================================================

    private ProfileSessionDTO buildSession(
            ParticipantSession participantSession
    ) {

        Session session = participantSession.getSession();

        Espace espace = session.getEspace();

        Sprint sprint = session.getSprint();

        TypeSessionCode type =
                session.getTypeSession().getCode();

        return ProfileSessionDTO.builder()

                .sessionId(session.getId())

                .type(type)

                .titre(buildSessionTitle(session))

                .statut(session.getStatus())

                .workspaceId(
                        espace != null
                                ? espace.getId()
                                : null
                )

                .workspaceName(
                        espace != null
                                ? espace.getNom()
                                : null
                )

                .sprintId(
                        sprint != null
                                ? sprint.getId()
                                : null
                )

                .sprintTitre(
                        sprint != null
                                ? sprint.getTitre()
                                : null
                )

                .commenceA(session.getCommenceA())

                .termineA(session.getTermineA())

                /*
                 * IMPORTANT:
                 * These come from ParticipantSession,
                 * NOT from Utilisateur.
                 */
                .roleSession(
                        participantSession.getRoleSession()
                )

                .pseudo(
                        participantSession.getPseudo()
                )

                .briefing(
                        buildSessionBriefing(session, type)
                )

                .build();
    }


    // ============================================================
    // SESSION TITLE
    // ============================================================

    private String buildSessionTitle(Session session) {

        TypeSessionCode type =
                session.getTypeSession().getCode();

        switch (type) {

            case POKER:
                return "Planning Poker #" + session.getId();

            case RETRO:
                return "Rétrospective #" + session.getId();

            case DAILY:
                return "Daily Scrum #" + session.getId();

            case REVIEW:
                return "Review #" + session.getId();

            default:
                return "Session #" + session.getId();
        }
    }


    // ============================================================
    // SESSION BRIEFING
    // ============================================================

    private SessionBriefingDTO buildSessionBriefing(
            Session session,
            TypeSessionCode type
    ) {

        return switch (type) {

            case POKER ->
                    buildPokerBriefing(session);

            case RETRO ->
                    buildRetroBriefing(session);

            case DAILY ->
                    buildDailyBriefing(session);

            case REVIEW ->
                    buildReviewBriefing(session);
        };
    }


    // ============================================================
    // POKER BRIEFING
    // ============================================================

    private SessionBriefingDTO buildPokerBriefing(
            Session session
    ) {

        int participants =
                session.getParticipants() == null
                        ? 0
                        : session.getParticipants().size();

        int storiesEstimated = 0;
        int rounds = 0;
        int totalStoryPoints = 0;

        if (session.getSelections() != null) {

            storiesEstimated =
                    (int) session.getSelections()
                            .stream()
                            .filter(selection ->
                                    selection.getTourEstimations() != null
                                            && !selection.getTourEstimations().isEmpty()
                            )
                            .count();

            for (SelectionUserStorySession selection :
                    session.getSelections()) {

                if (selection.getTourEstimations() == null) {
                    continue;
                }

                rounds += selection.getTourEstimations().size();

                for (TourEstimation tour :
                        selection.getTourEstimations()) {

                    if (tour.getValeurFinale() != null) {
                        totalStoryPoints += tour.getValeurFinale();
                    }
                }
            }
        }

        return SessionBriefingDTO.builder()
                .participants(participants)
                .storiesEstimated(storiesEstimated)
                .rounds(rounds)
                .totalStoryPoints(totalStoryPoints)
                .build();
    }

    // ============================================================
    // RETRO BRIEFING
    // ============================================================

    private SessionBriefingDTO buildRetroBriefing(
            Session session
    ) {

        int participants =
                session.getParticipants() == null
                        ? 0
                        : session.getParticipants().size();

        int groups =
                session.getGroupesNote() == null
                        ? 0
                        : session.getGroupesNote().size();

        int notes = 0;

        if (session.getGroupesNote() != null) {

            notes = session.getGroupesNote()
                    .stream()
                    .mapToInt(group ->
                            group.getNotesRetro() == null
                                    ? 0
                                    : group.getNotesRetro().size()
                    )
                    .sum();
        }

        int votes =
                session.getVotesDot() == null
                        ? 0
                        : session.getVotesDot().size();

        int actionItemsCreated =
                session.getActionItems() == null
                        ? 0
                        : session.getActionItems().size();

        return SessionBriefingDTO.builder()
                .participants(participants)
                .groups(groups)
                .notes(notes)
                .votes(votes)
                .actionItemsCreated(actionItemsCreated)
                .build();
    }


    // ============================================================
    // DAILY BRIEFING
    // ============================================================

    private SessionBriefingDTO buildDailyBriefing(
            Session session
    ) {

        int participants =
                session.getParticipants() == null
                        ? 0
                        : session.getParticipants().size();

        int updates = 0;
        int blockers = 0;

        if (session.getContenusDaily() != null) {

            updates = session.getContenusDaily().size();

            blockers = (int) session.getContenusDaily()
                    .stream()
                    .filter(content ->
                            content.getTypeContenu()
                                    == TypeDailyContent.BLOCKAGE)
                    .count();
        }

        return SessionBriefingDTO.builder()
                .participants(participants)
                .updates(updates)
                .blockers(blockers)
                .build();
    }


    // ============================================================
    // REVIEW BRIEFING
    // ============================================================

    private SessionBriefingDTO buildReviewBriefing(
            Session session
    ) {

        int participants =
                session.getParticipants() == null
                        ? 0
                        : session.getParticipants().size();

        /*
         * There is currently no Review-specific entity
         * in the domain that gives us additional metrics.
         *
         * Therefore we only expose the participant count.
         */

        return SessionBriefingDTO.builder()
                .participants(participants)
                .build();
    }


    // ============================================================
    // NOTIFICATION
    // ============================================================

    private ProfileNotificationDTO buildNotification(
            Notification notification
    ) {

        return ProfileNotificationDTO.builder()
                .notificationId(notification.getId())
                .description(notification.getDescription())

                /*
                 * luA == null means unread.
                 */
                .lue(notification.getLuA() != null)

                .envoyeA(notification.getEnvoyeA())

                .luA(notification.getLuA())

                .actionItemId(
                        notification.getActionItem() != null
                                ? notification.getActionItem().getId()
                                : null
                )

                .build();
    }
}