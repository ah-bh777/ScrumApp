
package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.DTOs.Invitation.AcceptInvitationRequest;
import com.ISICOD.ScrumApp.DTOs.Invitation.CreateInvitationRequest;
import com.ISICOD.ScrumApp.DTOs.Invitation.CreateInvitationResponse;
import com.ISICOD.ScrumApp.DTOs.Invitation.PendingInvitationResponse;

import java.util.List;

public interface InvitationService {

    // Scrum Master invites a user
    CreateInvitationResponse inviterUtilisateur(
            Integer espaceId,
            CreateInvitationRequest request
    );

    // User retrieves pending invitations
    List<PendingInvitationResponse> getPendingInvitations(
            Integer utilisateurId
    );

    // User accepts an invitation
    void accepterInvitation(
            Integer invitationId,
            AcceptInvitationRequest request
    );

    // User declines an invitation
    void refuserInvitation(
            Integer invitationId,
            AcceptInvitationRequest request
    );
}
