package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.Fonctionnalite;

import java.util.List;
import java.util.Optional;

public interface FonctionnaliteService {

    Fonctionnalite createFonctionnalite(Fonctionnalite fonctionnalite);

    Optional<Fonctionnalite> getFonctionnaliteById(Integer id);

    List<Fonctionnalite> getAllFonctionnalites();

    void deleteFonctionnalite(Integer id);
}