package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.DTOs.Profile.ProfileDTO;
import com.ISICOD.ScrumApp.Entities.*;
import com.ISICOD.ScrumApp.Repositories.*;
import com.ISICOD.ScrumApp.Services.Builders.ProfileBuilder;
import com.ISICOD.ScrumApp.Services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final UtilisateurRepository utilisateurRepository;
    private final AppartenanceRepository appartenanceRepository;
    private final InvitationRepository invitationRepository;
    private final ActionItemRepository actionItemRepository;
    private final ParticipantSessionRepository participantSessionRepository;
    private final NotificationRepository notificationRepository;

    private final ProfileBuilder profileBuilder;

    @Override
    public ProfileDTO getProfile(Integer utilisateurId) {

        // ==========================================
        // USER
        // ==========================================

        Utilisateur utilisateur =
                utilisateurRepository.findById(utilisateurId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Utilisateur introuvable : " + utilisateurId
                                )
                        );

        // ==========================================
        // WORKSPACES
        // Only memberships of this user
        // ==========================================

        List<Appartenance> appartenances =
                appartenanceRepository
                        .getAppartenanceByUtilisateurId(utilisateurId);

        // ==========================================
        // INVITATIONS
        // Only invitations belonging to this user
        // ==========================================

        List<Invitation> invitations =
                invitationRepository
                        .findByUtilisateurId(utilisateurId);

        // ==========================================
        // ACTION ITEMS
        // Only action items ASSIGNED to this user
        //
        // NOT findByCreateurId()
        // ==========================================

        List<ActionItem> actionItems =
                actionItemRepository
                        .findByAssigneAId(utilisateurId);

        // ==========================================
        // SCRUM SESSION PARTICIPATION
        //
        // ParticipantSession is important here because
        // pseudo + roleSession belong to the participation.
        // ==========================================

        List<ParticipantSession> participations =
                participantSessionRepository
                        .findByUtilisateurId(utilisateurId);

        // ==========================================
        // NOTIFICATIONS
        // Only notifications belonging to this user
        // ==========================================

        List<Notification> notifications =
                notificationRepository
                        .findByUtilisateurId(utilisateurId);

        // ==========================================
        // BUILD PROFILE
        // ==========================================

        return profileBuilder.build(
                utilisateur,
                appartenances,
                invitations,
                actionItems,
                participations,
                notifications
        );
    }
}