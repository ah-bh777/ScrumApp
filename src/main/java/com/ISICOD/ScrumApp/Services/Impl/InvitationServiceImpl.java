
package com.ISICOD.ScrumApp.Services.Impl;
import com.ISICOD.ScrumApp.Services.Impl.notificationPublisher.NotificationEventPublisher;


import com.ISICOD.ScrumApp.DTOs.Invitation.AcceptInvitationRequest;
import com.ISICOD.ScrumApp.DTOs.Invitation.CreateInvitationRequest;
import com.ISICOD.ScrumApp.DTOs.Invitation.CreateInvitationResponse;
import com.ISICOD.ScrumApp.DTOs.Invitation.PendingInvitationResponse;

import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Entities.Invitation;
import com.ISICOD.ScrumApp.Entities.Utilisateur;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import com.ISICOD.ScrumApp.Enums.StatutInvitation;

import com.ISICOD.ScrumApp.Exceptions.ConflictException;
import com.ISICOD.ScrumApp.Exceptions.ForbiddenException;
import com.ISICOD.ScrumApp.Exceptions.ResourceNotFoundException;

import com.ISICOD.ScrumApp.Entities.Notification;
import com.ISICOD.ScrumApp.Repositories.NotificationRepository;

import com.ISICOD.ScrumApp.Repositories.AppartenanceRepository;
import com.ISICOD.ScrumApp.Repositories.EspaceRepository;
import com.ISICOD.ScrumApp.Repositories.InvitationRepository;
import com.ISICOD.ScrumApp.Repositories.UtilisateurRepository;

import com.ISICOD.ScrumApp.Services.InvitationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final EspaceRepository espaceRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AppartenanceRepository appartenanceRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationEventPublisher notificationEventPublisher;


    // =====================================================
    // INVITE USER TO WORKSPACE
    // =====================================================

    @Override
    @Transactional
    public CreateInvitationResponse inviterUtilisateur(
            Integer espaceId,
            CreateInvitationRequest request
    ) {

        // 1. Validate request
        if (request == null
                || request.inviteurId() == null
                || request.email() == null
                || request.email().isBlank()
                || request.role() == null) {

            throw new IllegalArgumentException(
                    "L'identifiant de l'inviteur, l'email et le rôle sont obligatoires."
            );
        }

        // 2. Find workspace
        Espace espace = espaceRepository.findById(espaceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Espace introuvable : " + espaceId
                ));

        if (!Boolean.TRUE.equals(espace.getEstActive())) {
            throw new ConflictException("Cet espace est inactif.");
        }

        // 3. Verify inviter is a Scrum Master
        Appartenance appartenanceInviteur = appartenanceRepository
                .findByUtilisateurIdAndEspaceId(
                        request.inviteurId(),
                        espaceId
                )
                .orElseThrow(() -> new ForbiddenException(
                        "L'inviteur n'appartient pas à cet espace."
                ));

        if (appartenanceInviteur.getRoleAttribue()
                != RoleEspace.SCRUM_MASTER) {

            throw new ForbiddenException(
                    "Seul un Scrum Master peut inviter des membres."
            );
        }

        // 4. Validate proposed role
        if (request.role() == RoleEspace.SCRUM_MASTER) {
            throw new IllegalArgumentException(
                    "Vous ne pouvez pas attribuer le rôle SCRUM_MASTER."
            );
        }

        // 5. Find recipient
        String email = request.email().trim();

        Utilisateur destinataire = utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucun utilisateur trouvé avec cet email : " + email
                ));

        // 6. Prevent duplicate membership
        boolean dejaMembre = appartenanceRepository
                .findByUtilisateurIdAndEspaceId(
                        destinataire.getId(),
                        espaceId
                )
                .isPresent();

        if (dejaMembre) {
            throw new ConflictException(
                    "Cet utilisateur est déjà membre de cet espace."
            );
        }

        // 7. Prevent duplicate pending invitation
        boolean dejaInvite = invitationRepository
                .existsByUtilisateurIdAndEspaceIdAndStatut(
                        destinataire.getId(),
                        espaceId,
                        StatutInvitation.EN_ATTENTE
                );

        if (dejaInvite) {
            throw new ConflictException(
                    "Une invitation est déjà en attente pour cet utilisateur."
            );
        }

        // 8. Create invitation
        Invitation invitation = Invitation.builder()
                .code(UUID.randomUUID().toString())
                .expireA(LocalDateTime.now().plusDays(7))
                .estValide(true)
                .role(request.role())
                .espace(espace)
                .utilisateur(destinataire)
                .inviteur(appartenanceInviteur.getUtilisateur())
                .statut(StatutInvitation.EN_ATTENTE)
                .build();

        Invitation saved = invitationRepository.save(invitation);


        Notification notification = Notification.builder()
                .utilisateur(destinataire)
                .invitation(saved)
                .description(
                        "Vous avez été invité à rejoindre l'espace "
                                + espace.getNom()
                )
                .build();

        Notification savedNotification = notificationRepository.save(notification);

        notificationEventPublisher.publish(savedNotification);


        return new CreateInvitationResponse(
                saved.getId(),
                espace.getId(),
                destinataire.getId(),
                destinataire.getEmail(),
                saved.getRole(),
                saved.getStatut(),
                saved.getExpireA()
        );
    }

    // =====================================================
    // GET PENDING INVITATIONS
    // =====================================================

    @Override
    @Transactional(readOnly = true)
    public List<PendingInvitationResponse> getPendingInvitations(
            Integer utilisateurId
    ) {

        // 1. Validate user ID
        if (utilisateurId == null || utilisateurId <= 0) {
            throw new IllegalArgumentException(
                    "L'identifiant de l'utilisateur est invalide."
            );
        }

        // 2. Verify user exists
        if (!utilisateurRepository.existsById(utilisateurId)) {
            throw new ResourceNotFoundException(
                    "Utilisateur introuvable avec l'id : " + utilisateurId
            );
        }

        // 3. Retrieve pending invitations
        List<Invitation> invitations = invitationRepository
                .findByUtilisateurIdAndStatut(
                        utilisateurId,
                        StatutInvitation.EN_ATTENTE
                );

        LocalDateTime maintenant = LocalDateTime.now();

        // 4. Filter and map to DTO
        return invitations.stream()
                .filter(invitation ->
                        Boolean.TRUE.equals(invitation.getEstValide())
                )
                .filter(invitation ->
                        invitation.getExpireA() != null
                                && invitation.getExpireA().isAfter(maintenant)
                )
                .map(invitation -> new PendingInvitationResponse(
                        invitation.getId(),
                        invitation.getEspace().getId(),
                        invitation.getEspace().getNom(),
                        invitation.getEspace().getNomEquipe(),
                        invitation.getRole(),
                        invitation.getStatut(),
                        invitation.getExpireA()
                ))
                .toList();
    }

    // =====================================================
    // ACCEPT INVITATION
    // =====================================================

    @Override
    @Transactional
    public void accepterInvitation(
            Integer invitationId,
            AcceptInvitationRequest request
    ) {

        // 1. Validate request
        if (invitationId == null || invitationId <= 0
                || request == null
                || request.utilisateurId() == null
                || request.utilisateurId() <= 0) {

            throw new IllegalArgumentException(
                    "L'identifiant de l'invitation et de l'utilisateur sont obligatoires."
            );
        }

        // 2. Lock and retrieve invitation
        Invitation invitation = invitationRepository
                .findByIdForUpdate(invitationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invitation introuvable avec l'id : " + invitationId
                ));

        // 3. Verify recipient
        if (invitation.getUtilisateur() == null
                || !request.utilisateurId().equals(
                invitation.getUtilisateur().getId()
        )) {

            throw new ForbiddenException(
                    "Cette invitation ne vous est pas destinée."
            );
        }

        // 4. Verify invitation status
        if (invitation.getStatut() != StatutInvitation.EN_ATTENTE) {
            throw new ConflictException(
                    "Cette invitation a déjà été traitée."
            );
        }

        // 5. Verify validity and expiration
        if (!Boolean.TRUE.equals(invitation.getEstValide())
                || invitation.getExpireA() == null
                || !invitation.getExpireA().isAfter(LocalDateTime.now())) {

            throw new ConflictException(
                    "Cette invitation est invalide ou expirée."
            );
        }

        // 6. Retrieve workspace
        Espace espace = invitation.getEspace();

        if (espace == null) {
            throw new ResourceNotFoundException(
                    "L'espace associé à cette invitation est introuvable."
            );
        }

        if (!Boolean.TRUE.equals(espace.getEstActive())) {
            throw new ConflictException(
                    "Impossible de rejoindre un espace inactif."
            );
        }

        // 7. Prevent duplicate membership
        boolean dejaMembre = appartenanceRepository
                .findByUtilisateurIdAndEspaceId(
                        request.utilisateurId(),
                        espace.getId()
                )
                .isPresent();

        if (dejaMembre) {
            throw new ConflictException(
                    "Cet utilisateur est déjà membre de cet espace."
            );
        }

        // 8. Create membership
        Appartenance appartenance = new Appartenance();

        appartenance.setUtilisateur(invitation.getUtilisateur());
        appartenance.setEspace(espace);
        appartenance.setRoleAttribue(invitation.getRole());
        appartenance.setRejointA(LocalDateTime.now());

        appartenanceRepository.save(appartenance);

        // 9. Mark invitation as accepted
        invitation.setStatut(StatutInvitation.ACCEPTEE);
        invitation.setEstValide(false);

        invitationRepository.save(invitation);
    }

    // =====================================================
    // DECLINE INVITATION
    // =====================================================

    @Override
    @Transactional
    public void refuserInvitation(
            Integer invitationId,
            AcceptInvitationRequest request
    ) {

        // 1. Validate request
        if (invitationId == null || invitationId <= 0
                || request == null
                || request.utilisateurId() == null
                || request.utilisateurId() <= 0) {

            throw new IllegalArgumentException(
                    "L'identifiant de l'invitation et de l'utilisateur sont obligatoires."
            );
        }

        // 2. Lock and retrieve invitation
        Invitation invitation = invitationRepository
                .findByIdForUpdate(invitationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invitation introuvable avec l'id : " + invitationId
                ));

        // 3. Verify recipient
        if (invitation.getUtilisateur() == null
                || !request.utilisateurId().equals(
                invitation.getUtilisateur().getId()
        )) {

            throw new ForbiddenException(
                    "Cette invitation ne vous est pas destinée."
            );
        }

        // 4. Verify invitation status
        if (invitation.getStatut() != StatutInvitation.EN_ATTENTE) {
            throw new ConflictException(
                    "Cette invitation a déjà été traitée."
            );
        }

        // 5. Verify validity and expiration
        if (!Boolean.TRUE.equals(invitation.getEstValide())
                || invitation.getExpireA() == null
                || !invitation.getExpireA().isAfter(LocalDateTime.now())) {

            throw new ConflictException(
                    "Cette invitation est invalide ou expirée."
            );
        }

        // 6. Mark invitation as declined
        invitation.setStatut(StatutInvitation.REFUSEE);
        invitation.setEstValide(false);

        invitationRepository.save(invitation);
    }
}
