package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.TypeSessionFonctionnalite;

import java.util.List;
import java.util.Optional;

public interface TypeSessionFonctionnaliteService {

    TypeSessionFonctionnalite create(TypeSessionFonctionnalite entity);

    Optional<TypeSessionFonctionnalite> getById(Integer id);

    List<TypeSessionFonctionnalite> getAll();

    void delete(Integer id);
}