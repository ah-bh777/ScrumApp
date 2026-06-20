package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.TypeSession;
import com.ISICOD.ScrumApp.Repositories.TypeSessionRepository;
import com.ISICOD.ScrumApp.Services.TypeSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeSessionServiceImpl implements TypeSessionService {

    private final TypeSessionRepository typeSessionRepository;

    @Override
    public TypeSession createTypeSession(TypeSession typeSession) {
        return typeSessionRepository.save(typeSession);
    }

    @Override
    public Optional<TypeSession> getTypeSessionById(Integer id) {
        return typeSessionRepository.findById(id);
    }

    @Override
    public List<TypeSession> getAllTypeSessions() {
        return typeSessionRepository.findAll();
    }

    @Override
    public void deleteTypeSession(Integer id) {

        TypeSession typeSession = typeSessionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("TypeSession introuvable avec id : " + id));

        typeSessionRepository.delete(typeSession);
    }
}