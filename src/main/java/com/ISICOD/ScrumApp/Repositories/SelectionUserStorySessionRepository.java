package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.SelectionUserStorySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionUserStorySessionRepository extends JpaRepository<SelectionUserStorySession, Integer> {
}