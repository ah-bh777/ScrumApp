
package com.ISICOD.ScrumApp.DTOs.Invitation;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import com.ISICOD.ScrumApp.Enums.StatutInvitation;

import java.time.LocalDateTime;

public record PendingInvitationResponse(
        Integer invitationId,
        Integer espaceId,
        String espaceNom,
        String nomEquipe,
        RoleEspace role,
        StatutInvitation statut,
        LocalDateTime expireA
) {
}
