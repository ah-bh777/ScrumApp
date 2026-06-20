package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.SessionConfiguration;
import com.ISICOD.ScrumApp.Repositories.SessionConfigurationRepository;
import com.ISICOD.ScrumApp.Services.SessionConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionConfigurationServiceImpl implements SessionConfigurationService {

    private final SessionConfigurationRepository repository;

    @Override
    public SessionConfiguration create(SessionConfiguration config) {
        return repository.save(config);
    }

    @Override
    public Optional<SessionConfiguration> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<SessionConfiguration> getAll() {
        return repository.findAll();
    }

    @Override
    public SessionConfiguration update(Integer id, SessionConfiguration config) {

        SessionConfiguration existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("SessionConfiguration introuvable avec id : " + id));


        if (config.getValeur() != null) {
            existing.setValeur(config.getValeur());
        }

        if (config.getSession() != null) {
            existing.setSession(config.getSession());
        }

        if (config.getFonctionnalite() != null) {
            existing.setFonctionnalite(config.getFonctionnalite());
        }

        return repository.save(existing);
    }

    @Override
    public void delete(Integer id) {

        SessionConfiguration config = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("SessionConfiguration introuvable avec id : " + id));

        repository.delete(config);
    }
}