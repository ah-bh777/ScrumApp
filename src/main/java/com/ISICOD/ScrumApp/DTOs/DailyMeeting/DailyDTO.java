package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyDTO {

    private DailySessionDTO session;

    private DailyWorkspaceDTO workspace;

    private DailySprintDTO sprint;

    private List<DailyParticipantDTO> participants;

    private List<DailyContentDTO> contents;
}