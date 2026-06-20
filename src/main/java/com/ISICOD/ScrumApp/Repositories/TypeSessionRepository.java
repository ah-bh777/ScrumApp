package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.TypeSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeSessionRepository extends JpaRepository<TypeSession, Integer> {
}