package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.DTOs.Utilisateur.*;
import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Entities.Invitation;
import com.ISICOD.ScrumApp.Entities.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    Utilisateur createUtilisateur(Utilisateur utilisateur);

    Optional<?> getUtilisateurById(Integer id);

    List<Utilisateur> getAllUtilisateurs();

    Utilisateur updateUtilisateur(Integer id, Utilisateur utilisateur);

    void deleteUtilisateur(Integer id);

    public List<UtilisateurEspaceDTO> getUtilisateursEspace(Integer id);

    public List<UtilisateurInvitationDTO> getInvitationByUtilisateur(Integer utilisateurId);

    List<UtilisateurNotificationDTO> getNotificationsByUtilisateur(Integer utilisateurId);

    List<UtilisateurSessionDTO> getSessionsByUtilisateur(Integer utilisateurId);

    List<UtilisateurDailyContentDTO> getDailyContentsByUtilisateur(Integer utilisateurId);

    UtilisateurRetroDTO getRetroByUtilisateur(Integer utilisateurId);

    UtilisateurPokerDTO getPokerByUtilisateur(Integer utilisateurId);
}