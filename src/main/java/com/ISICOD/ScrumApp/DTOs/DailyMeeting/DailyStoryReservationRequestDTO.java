package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import lombok.Data;

@Data
public class DailyStoryReservationRequestDTO {

    private Integer utilisateurId;

    private Integer participantSessionId;

    private Integer sprintUserStoryId;
}
