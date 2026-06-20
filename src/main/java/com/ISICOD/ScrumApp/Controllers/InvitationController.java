package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.Invitation;
import com.ISICOD.ScrumApp.Services.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    public ResponseEntity<Invitation> createInvitation(
            @RequestBody Invitation invitation) {

        return ResponseEntity.ok(
                invitationService.createInvitation(invitation)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invitation> getInvitationById(
            @PathVariable Integer id) {

        return invitationService.getInvitationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Invitation>> getAllInvitations() {

        return ResponseEntity.ok(
                invitationService.getAllInvitations()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Invitation> updateInvitation(
            @PathVariable Integer id,
            @RequestBody Invitation invitation) {

        return ResponseEntity.ok(
                invitationService.updateInvitation(id, invitation)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(
            @PathVariable Integer id) {

        invitationService.deleteInvitation(id);

        return ResponseEntity.noContent().build();
    }
}