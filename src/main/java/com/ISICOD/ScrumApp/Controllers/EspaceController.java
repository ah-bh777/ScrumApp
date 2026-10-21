package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.DTOs.Espace.*;

import java.util.List;

import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Services.EspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import com.ISICOD.ScrumApp.DTOs.Invitation.CreateInvitationRequest;
import com.ISICOD.ScrumApp.DTOs.Invitation.CreateInvitationResponse;
import com.ISICOD.ScrumApp.Services.InvitationService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/espaces")
@RequiredArgsConstructor
public class EspaceController {

    private final EspaceService espaceService;
    private final InvitationService invitationService;


    @PostMapping
    public ResponseEntity<CreateEspaceResponse> createEspace(
            @RequestBody CreateEspaceRequest request
    ) {

        CreateEspaceResponse response =
                espaceService.createEspaceWithScrumMaster(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Espace> getEspaceById(
            @PathVariable Integer id) {

        return espaceService.getEspaceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Espace>> getAllEspaces() {

        return ResponseEntity.ok(
                espaceService.getAllEspaces()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Espace> updateEspace(
            @PathVariable Integer id,
            @RequestBody Espace espace) {

        return ResponseEntity.ok(
                espaceService.updateEspace(id, espace)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspace(
            @PathVariable Integer id) {

        espaceService.deleteEspace(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<EspaceDashboardDTO> getDashboard(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                espaceService.getDashboard(id)
        );
    }

    @GetMapping("/{espaceId}/members")
    public ResponseEntity<List<EspaceMemberListDTO>> getEspaceMembers(
            @PathVariable Integer espaceId
    ) {

        return ResponseEntity.ok(
                espaceService.getEspaceMembers(
                        espaceId
                )
        );
    }

    @GetMapping("/users/{utilisateurId}")
    public ResponseEntity<List<UserEspaceDTO>> getUserEspaces(
            @PathVariable Integer utilisateurId
    ) {

        return ResponseEntity.ok(
                espaceService.getEspacesByUtilisateur(utilisateurId)
        );
    }



    @PostMapping("/{espaceId}/invitations")
    public ResponseEntity<CreateInvitationResponse> inviterUtilisateur(
            @PathVariable Integer espaceId,
            @RequestBody CreateInvitationRequest request
    ) {
        CreateInvitationResponse response =
                invitationService.inviterUtilisateur(espaceId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



    @PatchMapping("/{espaceId}/members/{membreId}/role")
    public ResponseEntity<Void> changerRoleMembre(
            @PathVariable Integer espaceId,
            @PathVariable Integer membreId,
            @RequestBody ChangeMemberRoleRequest request
    ) {
        espaceService.changerRoleMembre(
                espaceId,
                membreId,
                request
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{espaceId}/members/{membreId}")
    public ResponseEntity<Void> supprimerMembre(
            @PathVariable Integer espaceId,
            @PathVariable Integer membreId,
            @RequestBody RemoveMemberRequest request
    ) {
        espaceService.supprimerMembre(espaceId, membreId, request);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{espaceId}/members/me")
    public ResponseEntity<Void> quitterEspace(
            @PathVariable Integer espaceId,
            @RequestBody LeaveWorkspaceRequest request
    ) {
        espaceService.quitterEspace(espaceId, request);

        return ResponseEntity.noContent().build();
    }

}