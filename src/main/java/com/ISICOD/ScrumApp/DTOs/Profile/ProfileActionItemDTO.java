package com.ISICOD.ScrumApp.DTOs.Profile;

import com.ISICOD.ScrumApp.Enums.StatutActionItem;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileActionItemDTO {

    private Integer actionItemId;

    private String titre;

    private String description;

    private StatutActionItem statut;

    private Integer workspaceId;

    private String workspaceName;

    private Integer sprintId;

    private String sprintTitre;

    private Integer sessionId;

    private LocalDateTime creeA;

    private LocalDate echeance;

    private LocalDateTime termineA;
}