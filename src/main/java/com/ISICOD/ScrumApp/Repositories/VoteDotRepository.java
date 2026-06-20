package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.VoteDot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteDotRepository extends JpaRepository<VoteDot, Integer> {
}