package com.ISICOD.ScrumApp.DTOs.ActionItem;

import com.ISICOD.ScrumApp.Enums.StatutActionItem;
import com.ISICOD.ScrumApp.Enums.StatutSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionItemDetailsDTO {

    private Integer id;
    private String titre;
    private String description;
    private LocalDate echeance;
    private StatutActionItem status;
    private LocalDateTime creeA;

    private UtilisateurResumeDTO createur;
    private UtilisateurResumeDTO assigneA;
    private SessionResumeDTO session;

    private List<NotificationResumeDTO> notifications;
}