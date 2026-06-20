package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.ParticipantSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantSessionRepository extends JpaRepository<ParticipantSession, Integer> {
}