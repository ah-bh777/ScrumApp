package com.ISICOD.ScrumApp.DTOs.Invitation;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import com.ISICOD.ScrumApp.Enums.StatutInvitation;

import java.time.LocalDateTime;

public record CreateInvitationResponse(
        Integer invitationId,
        Integer espaceId,
        Integer inviteId,
        String email,
        RoleEspace role,
        StatutInvitation statut,
        LocalDateTime expireA
) {
}