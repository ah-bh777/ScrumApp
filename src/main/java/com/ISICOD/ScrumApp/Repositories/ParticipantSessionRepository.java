package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.ParticipantSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantSessionRepository extends JpaRepository<ParticipantSession, Integer> {

    List<ParticipantSession> findByUtilisateurId(Integer utilisateurId);

    Optional<ParticipantSession> findBySessionIdAndUtilisateurId(
            Integer sessionId,
            Integer utilisateurId);
}