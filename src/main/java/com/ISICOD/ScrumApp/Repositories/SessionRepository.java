package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.Session;
import com.ISICOD.ScrumApp.Enums.StatutSession;
import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findFirstByEspaceIdAndTypeSessionCodeAndStatusOrderByIdDesc(
            Integer espaceId,
            TypeSessionCode typeSessionCode,
            StatutSession status
    );
}
