package com.ISICOD.ScrumApp.DTOs.Profile;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileWorkspaceDTO {

    private Integer espaceId;

    private String workspaceName;

    private RoleEspace role;

    private LocalDateTime rejointA;

    private Boolean estActive;
}