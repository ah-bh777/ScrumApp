package com.ISICOD.ScrumApp.DTOs.Invitation;

import com.ISICOD.ScrumApp.Enums.RoleEspace;

public record CreateInvitationRequest(
        Integer inviteurId,
        String email,
        RoleEspace role
) {
}