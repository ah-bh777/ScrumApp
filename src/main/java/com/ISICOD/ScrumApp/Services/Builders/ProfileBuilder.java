package com.ISICOD.ScrumApp.Services.Builders;

import com.ISICOD.ScrumApp.DTOs.Profile.ProfileDTO;
import com.ISICOD.ScrumApp.Entities.*;

import java.util.List;

public interface ProfileBuilder {

    ProfileDTO build(
            Utilisateur utilisateur,
            List<Appartenance> appartenances,
            List<Invitation> invitations,
            List<ActionItem> actionItems,
            List<ParticipantSession> participations,
            List<Notification> notifications
    );

}