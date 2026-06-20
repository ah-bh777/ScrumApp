package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.Session;

import java.util.List;
import java.util.Optional;

public interface SessionService {

    Session createSession(Session session);

    Optional<Session> getSessionById(Integer id);

    List<Session> getAllSessions();

    Session updateSession(Integer id, Session session);

    void deleteSession(Integer id);
}