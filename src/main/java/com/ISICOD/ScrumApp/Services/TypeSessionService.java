package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.TypeSession;

import java.util.List;
import java.util.Optional;

public interface TypeSessionService {

    TypeSession createTypeSession(TypeSession typeSession);

    Optional<TypeSession> getTypeSessionById(Integer id);

    List<TypeSession> getAllTypeSessions();

    void deleteTypeSession(Integer id);
}