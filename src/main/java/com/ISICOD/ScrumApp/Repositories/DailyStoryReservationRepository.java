
package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.DailyStoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyStoryReservationRepository
        extends JpaRepository<DailyStoryReservation, Integer> {

    Optional<DailyStoryReservation>
    findBySessionIdAndSprintUserStoryId(
            Integer sessionId,
            Integer sprintUserStoryId
    );

    List<DailyStoryReservation>
    findBySessionId(Integer sessionId);

    List<DailyStoryReservation>
    findByParticipantSessionId(Integer participantSessionId);

    List<DailyStoryReservation>
    findByExpiresAtBefore(LocalDateTime now);
}
