package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.DTOs.Daily.DailySessionDTO;
import com.ISICOD.ScrumApp.DTOs.Retro.RetroSessionDTO;
import com.ISICOD.ScrumApp.DTOs.Session.ConfigurationDTO;
import com.ISICOD.ScrumApp.DTOs.Session.ParticipantDTO;
import com.ISICOD.ScrumApp.DTOs.Session.SessionDetailsDTO;
import com.ISICOD.ScrumApp.DTOs.Session.SprintDTO;
import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Entities.Session;
import com.ISICOD.ScrumApp.Entities.Sprint;
import com.ISICOD.ScrumApp.Repositories.*;
import com.ISICOD.ScrumApp.Services.Builders.DailyBuilder;
import com.ISICOD.ScrumApp.Services.Builders.RetroBuilder;
import com.ISICOD.ScrumApp.Services.Builders.SessionDetailsBuilder;
import com.ISICOD.ScrumApp.Services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    private final SessionDetailsBuilder sessionDetailsBuilder;

    private final DailyBuilder dailyBuilder;

    private final RetroBuilder retroBuilder;

    @Override
    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Optional<Session> getSessionById(Integer id) {
        return sessionRepository.findById(id);
    }

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public Session updateSession(Integer id, Session session) {

        Session existing = sessionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Session introuvable avec id : " + id));

        if (session.getCommenceA() != null)
            existing.setCommenceA(session.getCommenceA());

        if (session.getTermineA() != null)
            existing.setTermineA(session.getTermineA());

        if (session.getStatus() != null)
            existing.setStatus(session.getStatus());

        if (session.getCreaA() != null)
            existing.setCreaA(session.getCreaA());

        if (session.getEspace() != null)
            existing.setEspace(session.getEspace());

        if (session.getSprint() != null)
            existing.setSprint(session.getSprint());

        if (session.getTypeSession() != null)
            existing.setTypeSession(session.getTypeSession());

        return sessionRepository.save(existing);
    }

    @Override
    public void deleteSession(Integer id) {

        Session session = sessionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Session introuvable avec id : " + id));

        sessionRepository.delete(session);
    }

    @Override
    public SessionDetailsDTO getSessionDetails(Integer sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Session introuvable avec id : " + sessionId));

        return sessionDetailsBuilder.build(session);
    }

    @Override
    public DailySessionDTO getDailySession(Integer sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Session introuvable avec id : " + sessionId));

        return dailyBuilder.build(session);
    }

    @Override
    public RetroSessionDTO getRetroSession(Integer sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Session introuvable avec id : " + sessionId));

        return retroBuilder.build(session);
    }
}