package com.ISICOD.ScrumApp.DTOs.Session;


import com.ISICOD.ScrumApp.Enums.StatutSession;
import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDetailsDTO {

    private Integer sessionId;

    private LocalDateTime commenceA;

    private LocalDateTime termineA;

    private StatutSession statut;

    private TypeSessionCode typeSession;

    private SprintDTO sprint;

    private List<ParticipantDTO> participants;

    private List<ConfigurationDTO> configurations;

    private Object contenu;
}