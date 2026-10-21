package com.ISICOD.ScrumApp.Services.Builders.DailyMeeting;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.*;
import com.ISICOD.ScrumApp.Entities.*;

import java.util.List;

public interface DailyMeetingBuilder {

    DailyDTO buildDaily(
            Session session,
            List<ParticipantSession> participants,
            List<DailyContent> contents
    );

    DailySessionDTO buildSession(Session session);

    DailyWorkspaceDTO buildWorkspace(Espace espace);

    DailySprintDTO buildSprint(Sprint sprint);

    DailyParticipantDTO buildParticipant(
            ParticipantSession participant,
            List<DailyContent> contents
    );

    DailyContentDTO buildContent(DailyContent content);

    DailyStoryOptionDTO buildStoryOption(SprintUserStory sprintUserStory);
}