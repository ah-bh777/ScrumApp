package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.VoteDot;
import com.ISICOD.ScrumApp.Repositories.VoteDotRepository;
import com.ISICOD.ScrumApp.Services.VoteDotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteDotServiceImpl implements VoteDotService {

    private final VoteDotRepository voteDotRepository;

    @Override
    public VoteDot createVoteDot(VoteDot voteDot) {
        return voteDotRepository.save(voteDot);
    }

    @Override
    public Optional<VoteDot> getVoteDotById(Integer id) {
        return voteDotRepository.findById(id);
    }

    @Override
    public List<VoteDot> getVoteDotAll() {
        return voteDotRepository.findAll();
    }

    @Override
    public VoteDot updateVoteDot(Integer id, VoteDot voteDot) {

        VoteDot existing = voteDotRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("VoteDot introuvable avec id : " + id));


        if (voteDot.getCreeA() != null) {
            existing.setCreeA(voteDot.getCreeA());
        }

        if (voteDot.getUtilisateur() != null) {
            existing.setUtilisateur(voteDot.getUtilisateur());
        }

        if (voteDot.getSession() != null) {
            existing.setSession(voteDot.getSession());
        }

        if (voteDot.getParticipantSession() != null) {
            existing.setParticipantSession(voteDot.getParticipantSession());
        }

        if (voteDot.getNoteRetro() != null) {
            existing.setNoteRetro(voteDot.getNoteRetro());
        }

        return voteDotRepository.save(existing);
    }

    @Override
    public void deleteVoteDot(Integer id) {

        VoteDot existing = voteDotRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("VoteDot introuvable avec id : " + id));

        voteDotRepository.delete(existing);
    }
}