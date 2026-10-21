package com.ISICOD.ScrumApp.DTOs.Profile;

import com.ISICOD.ScrumApp.Enums.RoleSession;
import com.ISICOD.ScrumApp.Enums.StatutSession;
import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSessionDTO {

    private Integer sessionId;

    private TypeSessionCode type;

    private String titre;

    private StatutSession statut;

    private Integer workspaceId;

    private String workspaceName;

    private Integer sprintId;

    private String sprintTitre;

    private LocalDateTime commenceA;

    private LocalDateTime termineA;

    private RoleSession roleSession;

    private String pseudo;

    private SessionBriefingDTO briefing;
}