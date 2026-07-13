package com.ISICOD.ScrumApp.DTOs.Utilisateur;


import com.ISICOD.ScrumApp.Enums.StatutActionItem;
import com.ISICOD.ScrumApp.Enums.StatutSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurNotificationDTO {


    private Integer notificationId;
    private String description;
    private LocalDateTime envoyeA;
    private LocalDateTime luA;

    private Integer actionItemId;
    private String titre;
    private String actionDescription;
    private LocalDate echeance;
    private StatutActionItem status;
    private LocalDateTime creeA;

    private Integer assigneAId;
    private String assigneANom;
    private String assigneAPrenom;
    private String assigneAEmail;

    private Integer createurId;
    private String createurNom;
    private String createurPrenom;
    private String createurEmail;

    private Integer sessionId;
    private LocalDateTime commencerA;
    private LocalDateTime termineA;
    private StatutSession statusSession;
}