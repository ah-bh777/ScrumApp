package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.Utilisateur;
import com.ISICOD.ScrumApp.Repositories.UtilisateurRepository;
import com.ISICOD.ScrumApp.Services.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Optional<Utilisateur> getUtilisateurById(Integer id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur updateUtilisateur(Integer id, Utilisateur utilisateur) {

        Utilisateur existingUtilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable avec l'id : " + id));


        if (utilisateur.getNom() != null) {
            existingUtilisateur.setNom(utilisateur.getNom());
        }

        if (utilisateur.getPrenom() != null) {
            existingUtilisateur.setPrenom(utilisateur.getPrenom());
        }

        if (utilisateur.getTelephone() != null) {
            existingUtilisateur.setTelephone(utilisateur.getTelephone());
        }

        if (utilisateur.getEmail() != null) {
            existingUtilisateur.setEmail(utilisateur.getEmail());
        }

        return utilisateurRepository.save(existingUtilisateur);
    }

    @Override
    public void deleteUtilisateur(Integer id) {

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable avec l'id : " + id));

        utilisateurRepository.delete(utilisateur);
    }
}