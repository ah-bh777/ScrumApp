package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.EtatRetro;
import com.ISICOD.ScrumApp.Repositories.EtatRetroRepository;
import com.ISICOD.ScrumApp.Services.EtatRetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EtatRetroServiceImpl implements EtatRetroService {

    private final EtatRetroRepository repository;

    @Override
    public EtatRetro create(EtatRetro etatRetro) {
        return repository.save(etatRetro);
    }

    @Override
    public Optional<EtatRetro> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<EtatRetro> getAll() {
        return repository.findAll();
    }

    @Override
    public EtatRetro update(Integer id, EtatRetro etatRetro) {

        EtatRetro existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("EtatRetro introuvable avec id : " + id));

        if (etatRetro.getPhaseActuelle() != null) {
            existing.setPhaseActuelle(etatRetro.getPhaseActuelle());
        }

        if (etatRetro.getCommenceA() != null) {
            existing.setCommenceA(etatRetro.getCommenceA());
        }

        if (etatRetro.getTermineA() != null) {
            existing.setTermineA(etatRetro.getTermineA());
        }

        if (etatRetro.getTypeTemplate() != null) {
            existing.setTypeTemplate(etatRetro.getTypeTemplate());
        }

        if (etatRetro.getSession() != null) {
            existing.setSession(etatRetro.getSession());
        }

        return repository.save(existing);
    }

    @Override
    public void delete(Integer id) {

        EtatRetro etatRetro = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("EtatRetro introuvable avec id : " + id));

        repository.delete(etatRetro);
    }
}