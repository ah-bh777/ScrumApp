package com.ISICOD.ScrumApp.DTOs.Profile;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import com.ISICOD.ScrumApp.Enums.StatutInvitation;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInvitationDTO {

    private Integer invitationId;

    private Integer espaceId;

    private String workspaceName;

    private RoleEspace role;

    private StatutInvitation statut;

    private LocalDateTime expireA;

    private Boolean estValide;
}