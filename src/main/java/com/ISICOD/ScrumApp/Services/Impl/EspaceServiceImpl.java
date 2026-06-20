package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Repositories.EspaceRepository;
import com.ISICOD.ScrumApp.Services.EspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EspaceServiceImpl implements EspaceService {

    private final EspaceRepository espaceRepository;

    @Override
    public Espace createEspace(Espace espace) {
        return espaceRepository.save(espace);
    }

    @Override
    public Optional<Espace> getEspaceById(Integer id) {
        return espaceRepository.findById(id);
    }

    @Override
    public List<Espace> getAllEspaces() {
        return espaceRepository.findAll();
    }

    @Override
    public Espace updateEspace(Integer id, Espace espace) {

        Espace existingEspace = espaceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Espace introuvable avec l'id : " + id));


        if (espace.getNom() != null) {
            existingEspace.setNom(espace.getNom());
        }

        if (espace.getNomEquipe() != null) {
            existingEspace.setNomEquipe(espace.getNomEquipe());
        }

        if (espace.getEstActive() != null) {
            existingEspace.setEstActive(espace.getEstActive());
        }

        if (espace.getCapacite() != null) {
            existingEspace.setCapacite(espace.getCapacite());
        }

        return espaceRepository.save(existingEspace);
    }

    @Override
    public void deleteEspace(Integer id) {

        Espace espace = espaceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Espace introuvable avec l'id : " + id));

        espaceRepository.delete(espace);
    }
}