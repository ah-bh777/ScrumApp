package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.Fonctionnalite;
import com.ISICOD.ScrumApp.Repositories.FonctionnaliteRepository;
import com.ISICOD.ScrumApp.Services.FonctionnaliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FonctionnaliteServiceImpl implements FonctionnaliteService {

    private final FonctionnaliteRepository fonctionnaliteRepository;

    @Override
    public Fonctionnalite createFonctionnalite(Fonctionnalite fonctionnalite) {
        return fonctionnaliteRepository.save(fonctionnalite);
    }

    @Override
    public Optional<Fonctionnalite> getFonctionnaliteById(Integer id) {
        return fonctionnaliteRepository.findById(id);
    }

    @Override
    public List<Fonctionnalite> getAllFonctionnalites() {
        return fonctionnaliteRepository.findAll();
    }

    @Override
    public void deleteFonctionnalite(Integer id) {

        Fonctionnalite f = fonctionnaliteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Fonctionnalite introuvable avec id : " + id));

        fonctionnaliteRepository.delete(f);
    }
}