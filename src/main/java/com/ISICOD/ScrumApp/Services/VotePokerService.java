package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.VotePoker;

import java.util.List;
import java.util.Optional;

public interface VotePokerService {

    VotePoker createVotePoker(VotePoker votePoker);

    Optional<VotePoker> getVotePokerById(Integer id);

    List<VotePoker> getVotePokerAll();

    VotePoker updateVotePoker(Integer id, VotePoker votePoker);

    void deleteVotePoker(Integer id);
}