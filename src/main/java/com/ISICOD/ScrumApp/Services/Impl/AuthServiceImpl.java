package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.DTOs.Auth.LoginRequestDTO;
import com.ISICOD.ScrumApp.DTOs.Auth.LoginResponseDTO;
import com.ISICOD.ScrumApp.Entities.Utilisateur;
import com.ISICOD.ScrumApp.Repositories.UtilisateurRepository;
import com.ISICOD.ScrumApp.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        Utilisateur utilisateur =
                utilisateurRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Email ou mot de passe incorrect."
                                )
                        );

        if (!utilisateur.getPassword().equals(request.getPassword())) {
            throw new RuntimeException(
                    "Email ou mot de passe incorrect."
            );
        }

        return LoginResponseDTO.builder()
                .utilisateurId(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .build();
    }
}