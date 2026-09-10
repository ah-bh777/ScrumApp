package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.TypeSession;
import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeSessionRepository extends JpaRepository<TypeSession, Integer> {
    Optional<TypeSession> findByCode(TypeSessionCode code);
}