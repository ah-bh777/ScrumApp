package com.ISICOD.ScrumApp.Services.DailyMeeting;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.*;

import java.util.List;

public interface DailyService {

    DailyDTO getDaily(Integer sessionId);

    DailyDTO getActiveDaily(Integer espaceId);

    DailyDTO leaveDaily(
            Integer sessionId,
            Integer participantId
    );

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

    DailyDTO submitDaily(
            Integer sessionId,
            DailySubmissionRequestDTO request
    );

    DailyDTO resetMySubmission(
            Integer sessionId,
            Integer utilisateurId
    );
}