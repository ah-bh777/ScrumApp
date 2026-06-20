package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    Utilisateur createUtilisateur(Utilisateur utilisateur);

    Optional<?> getUtilisateurById(Integer id);

    List<Utilisateur> getAllUtilisateurs();

    Utilisateur updateUtilisateur(Integer id, Utilisateur utilisateur);

    void deleteUtilisateur(Integer id);
}