
package com.ISICOD.ScrumApp.Services.DailyMeeting;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyStoryReservationDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyStoryReservationRequestDTO;

import java.util.List;

public interface DailyStoryReservationService {

    DailyStoryReservationDTO reserveStory(
            Integer sessionId,
            DailyStoryReservationRequestDTO request
    );

    void releaseStory(
            Integer sessionId,
            DailyStoryReservationRequestDTO request
    );

    List<DailyStoryReservationDTO> getReservations(
            Integer sessionId
    );
}
