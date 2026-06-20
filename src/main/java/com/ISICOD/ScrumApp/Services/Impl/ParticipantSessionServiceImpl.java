package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.ParticipantSession;
import com.ISICOD.ScrumApp.Repositories.ParticipantSessionRepository;
import com.ISICOD.ScrumApp.Services.ParticipantSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantSessionServiceImpl implements ParticipantSessionService {

    private final ParticipantSessionRepository participantSessionRepository;

    @Override
    public ParticipantSession createParticipantSession(ParticipantSession participantSession) {
        return participantSessionRepository.save(participantSession);
    }

    @Override
    public Optional<ParticipantSession> getParticipantSessionById(Integer id) {
        return participantSessionRepository.findById(id);
    }

    @Override
    public List<ParticipantSession> getParticipantSessionAll() {
        return participantSessionRepository.findAll();
    }

    @Override
    public ParticipantSession updateParticipantSession(Integer id, ParticipantSession participantSession) {

        ParticipantSession existing = participantSessionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ParticipantSession introuvable avec id : " + id));


        if (participantSession.getPseudo() != null) {
            existing.setPseudo(participantSession.getPseudo());
        }

        if (participantSession.getEstInvite() != null) {
            existing.setEstInvite(participantSession.getEstInvite());
        }

        if (participantSession.getRoleSession() != null) {
            existing.setRoleSession(participantSession.getRoleSession());
        }

        if (participantSession.getUtilisateur() != null) {
            existing.setUtilisateur(participantSession.getUtilisateur());
        }

        if (participantSession.getSession() != null) {
            existing.setSession(participantSession.getSession());
        }

        return participantSessionRepository.save(existing);
    }

    @Override
    public void deleteParticipantSession(Integer id) {

        ParticipantSession participantSession =
                participantSessionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "ParticipantSession introuvable avec id : " + id));

        participantSessionRepository.delete(participantSession);
    }
}