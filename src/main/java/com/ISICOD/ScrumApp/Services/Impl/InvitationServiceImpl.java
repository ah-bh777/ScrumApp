package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.Invitation;
import com.ISICOD.ScrumApp.Repositories.InvitationRepository;
import com.ISICOD.ScrumApp.Services.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    @Override
    public Invitation createInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    @Override
    public Optional<Invitation> getInvitationById(Integer id) {
        return invitationRepository.findById(id);
    }

    @Override
    public List<Invitation> getAllInvitations() {
        return invitationRepository.findAll();
    }

    @Override
    public Invitation updateInvitation(Integer id, Invitation invitation) {

        Invitation existingInvitation = invitationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Invitation introuvable avec l'id : " + id));

        if (invitation.getCode() != null) {
            existingInvitation.setCode(invitation.getCode());
        }

        if (invitation.getExpireA() != null) {
            existingInvitation.setExpireA(invitation.getExpireA());
        }

        if (invitation.getEstValide() != null) {
            existingInvitation.setEstValide(invitation.getEstValide());
        }

        if (invitation.getRole() != null) {
            existingInvitation.setRole(invitation.getRole());
        }

        if (invitation.getEspace() != null) {
            existingInvitation.setEspace(invitation.getEspace());
        }

        if (invitation.getUtilisateur() != null) {
            existingInvitation.setUtilisateur(invitation.getUtilisateur());
        }

        return invitationRepository.save(existingInvitation);
    }

    @Override
    public void deleteInvitation(Integer id) {

        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Invitation introuvable avec l'id : " + id));

        invitationRepository.delete(invitation);
    }
}