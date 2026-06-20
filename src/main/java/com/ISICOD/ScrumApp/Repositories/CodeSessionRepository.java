package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.CodeSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeSessionRepository extends JpaRepository<CodeSession, Integer> {
}