package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.ParticipantSession;

import java.util.List;
import java.util.Optional;

public interface ParticipantSessionService {

    ParticipantSession createParticipantSession(ParticipantSession participantSession);

    Optional<ParticipantSession> getParticipantSessionById(Integer id);

    List<ParticipantSession> getParticipantSessionAll();

    ParticipantSession updateParticipantSession(Integer id, ParticipantSession participantSession);

    void deleteParticipantSession(Integer id);
}