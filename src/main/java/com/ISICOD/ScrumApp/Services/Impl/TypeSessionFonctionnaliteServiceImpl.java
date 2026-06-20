package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.TypeSessionFonctionnalite;
import com.ISICOD.ScrumApp.Repositories.TypeSessionFonctionnaliteRepository;
import com.ISICOD.ScrumApp.Services.TypeSessionFonctionnaliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeSessionFonctionnaliteServiceImpl implements TypeSessionFonctionnaliteService {

    private final TypeSessionFonctionnaliteRepository repository;

    @Override
    public TypeSessionFonctionnalite create(TypeSessionFonctionnalite entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<TypeSessionFonctionnalite> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<TypeSessionFonctionnalite> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Integer id) {

        TypeSessionFonctionnalite entity = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("TypeSessionFonctionnalite introuvable avec id : " + id));

        repository.delete(entity);
    }
}