package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.Invitation;

import java.util.List;
import java.util.Optional;

public interface InvitationService {

    Invitation createInvitation(Invitation invitation);

    Optional<Invitation> getInvitationById(Integer id);

    List<Invitation> getAllInvitations();

    Invitation updateInvitation(Integer id, Invitation invitation);

    void deleteInvitation(Integer id);
}