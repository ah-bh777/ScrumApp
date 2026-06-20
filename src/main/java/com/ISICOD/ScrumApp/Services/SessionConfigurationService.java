package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.SessionConfiguration;

import java.util.List;
import java.util.Optional;

public interface SessionConfigurationService {

    SessionConfiguration create(SessionConfiguration config);

    Optional<SessionConfiguration> getById(Integer id);

    List<SessionConfiguration> getAll();

    SessionConfiguration update(Integer id, SessionConfiguration config);

    void delete(Integer id);
}