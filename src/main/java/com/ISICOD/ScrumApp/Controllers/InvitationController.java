
package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.DTOs.Invitation.AcceptInvitationRequest;
import com.ISICOD.ScrumApp.Services.InvitationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    // ==========================================
    // ACCEPT INVITATION
    // ==========================================

    @PostMapping("/{id}/accept")
    public ResponseEntity<Void> accepterInvitation(
            @PathVariable Integer id,
            @RequestBody AcceptInvitationRequest request
    ) {

        invitationService.accepterInvitation(id, request);

        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // DECLINE INVITATION
    // ==========================================

    @PostMapping("/{id}/decline")
    public ResponseEntity<Void> refuserInvitation(
            @PathVariable Integer id,
            @RequestBody AcceptInvitationRequest request
    ) {

        invitationService.refuserInvitation(id, request);

        return ResponseEntity.noContent().build();
    }
}
