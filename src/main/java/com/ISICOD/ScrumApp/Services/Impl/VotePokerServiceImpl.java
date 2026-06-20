package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.VotePoker;
import com.ISICOD.ScrumApp.Repositories.VotePokerRepository;
import com.ISICOD.ScrumApp.Services.VotePokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VotePokerServiceImpl implements VotePokerService {

    private final VotePokerRepository votePokerRepository;

    @Override
    public VotePoker createVotePoker(VotePoker votePoker) {
        return votePokerRepository.save(votePoker);
    }

    @Override
    public Optional<VotePoker> getVotePokerById(Integer id) {
        return votePokerRepository.findById(id);
    }

    @Override
    public List<VotePoker> getVotePokerAll() {
        return votePokerRepository.findAll();
    }

    @Override
    public VotePoker updateVotePoker(Integer id, VotePoker votePoker) {

        VotePoker existing = votePokerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("VotePoker introuvable avec id : " + id));

        if (votePoker.getStatut() != null) {
            existing.setStatut(votePoker.getStatut());
        }

        if (votePoker.getValeur() != null) {
            existing.setValeur(votePoker.getValeur());
        }

        if (votePoker.getCreeA() != null) {
            existing.setCreeA(votePoker.getCreeA());
        }

        if (votePoker.getUtilisateur() != null) {
            existing.setUtilisateur(votePoker.getUtilisateur());
        }

        if (votePoker.getParticipantSession() != null) {
            existing.setParticipantSession(votePoker.getParticipantSession());
        }

        if (votePoker.getTourEstimation() != null) {
            existing.setTourEstimation(votePoker.getTourEstimation());
        }

        return votePokerRepository.save(existing);
    }

    @Override
    public void deleteVotePoker(Integer id) {

        VotePoker existing = votePokerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("VotePoker introuvable avec id : " + id));

        votePokerRepository.delete(existing);
    }
}