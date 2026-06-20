package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.VoteDot;

import java.util.List;
import java.util.Optional;

public interface VoteDotService {

    VoteDot createVoteDot(VoteDot voteDot);

    Optional<VoteDot> getVoteDotById(Integer id);

    List<VoteDot> getVoteDotAll();

    VoteDot updateVoteDot(Integer id, VoteDot voteDot);

    void deleteVoteDot(Integer id);
}