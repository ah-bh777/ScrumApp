package com.ISICOD.ScrumApp.Services.DailyMeeting;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyContentRequestDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyContentUpdateDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailySessionRequestDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyStoryOptionDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.ParticipantJoinRequestDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.SessionStatusUpdateDTO;

import java.util.List;

public interface DailyService {

    DailyDTO getDaily(Integer sessionId);

    List<DailyStoryOptionDTO> getDailyStories(Integer sprintId);

    DailyDTO createDaily(
            Integer espaceId,
            Integer sprintId,
            Integer utilisateurId,
            DailySessionRequestDTO request
    );

    DailyDTO joinDaily(
            Integer sessionId,
            ParticipantJoinRequestDTO request
    );

    DailyDTO addContent(
            Integer sessionId,
            DailyContentRequestDTO request
    );

    DailyDTO updateContent(
            Integer sessionId,
            Integer dailyContentId,
            DailyContentUpdateDTO request
    );

    void deleteContent(
            Integer sessionId,
            Integer dailyContentId,
            Integer utilisateurId
    );

    DailyDTO updateSessionStatus(
            Integer sessionId,
            Integer utilisateurId,
            SessionStatusUpdateDTO request
    );
}