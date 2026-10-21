
package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.ParticipantSession;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantSessionRepository
        extends JpaRepository<ParticipantSession, Integer> {

    List<ParticipantSession> findByUtilisateurId(
            Integer utilisateurId
    );

    Optional<ParticipantSession> findBySessionIdAndUtilisateurId(
            Integer sessionId,
            Integer utilisateurId
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT p
        FROM ParticipantSession p
        WHERE p.id = :participantId
          AND p.session.id = :sessionId
        """)
    Optional<ParticipantSession> findForSubmission(
            @Param("participantId") Integer participantId,
            @Param("sessionId") Integer sessionId
    );

    List<ParticipantSession> findBySessionIdOrderByIdAsc(Integer sessionId);
}
