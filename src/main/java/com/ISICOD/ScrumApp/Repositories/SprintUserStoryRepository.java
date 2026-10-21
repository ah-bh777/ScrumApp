package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.SprintUserStory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SprintUserStoryRepository
        extends JpaRepository<SprintUserStory, Integer> {

    List<SprintUserStory> findByUserStoryId(Integer userStoryId);

    List<SprintUserStory> findBySprintId(Integer sprintId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT s
    FROM SprintUserStory s
    WHERE s.id = :storyId
    """)
    Optional<SprintUserStory> findByIdForReservation(
            @Param("storyId") Integer storyId
    );
}