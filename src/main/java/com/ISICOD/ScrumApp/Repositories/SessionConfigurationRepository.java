package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.SessionConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionConfigurationRepository extends JpaRepository<SessionConfiguration, Integer> {
}