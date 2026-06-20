package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.CodeSession;
import com.ISICOD.ScrumApp.Repositories.CodeSessionRepository;
import com.ISICOD.ScrumApp.Services.CodeSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CodeSessionServiceImpl implements CodeSessionService {

    private final CodeSessionRepository codeSessionRepository;

    @Override
    public CodeSession createCodeSession(CodeSession codeSession) {
        return codeSessionRepository.save(codeSession);
    }

    @Override
    public Optional<CodeSession> getCodeSessionById(Integer id) {
        return codeSessionRepository.findById(id);
    }

    @Override
    public List<CodeSession> getAllCodeSessions() {
        return codeSessionRepository.findAll();
    }

    @Override
    public void deleteCodeSession(Integer id) {

        CodeSession codeSession = codeSessionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("CodeSession introuvable avec id : " + id));

        codeSessionRepository.delete(codeSession);
    }
}