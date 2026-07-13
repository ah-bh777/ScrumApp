package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.VotePoker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotePokerRepository extends JpaRepository<VotePoker, Integer> {

    List<VotePoker> findByUtilisateurId(Integer utilisateurId);

}