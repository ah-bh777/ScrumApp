package com.ISICOD.ScrumApp.DTOs.Profile;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private ProfileUserDTO utilisateur;

    private List<ProfileWorkspaceDTO> appartenances;

    private List<ProfileInvitationDTO> invitations;

    private List<ProfileActionItemDTO> actionItems;

    private List<ProfileSessionDTO> sessions;

    private List<ProfileNotificationDTO> notifications;
}

