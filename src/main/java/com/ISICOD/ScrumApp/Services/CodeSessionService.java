package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.CodeSession;

import java.util.List;
import java.util.Optional;

public interface CodeSessionService {

    CodeSession createCodeSession(CodeSession codeSession);

    Optional<CodeSession> getCodeSessionById(Integer id);

    List<CodeSession> getAllCodeSessions();

    void deleteCodeSession(Integer id);
}