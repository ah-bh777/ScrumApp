package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.Utilisateur;
import com.ISICOD.ScrumApp.Services.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(
            @RequestBody Utilisateur utilisateur) {

        return ResponseEntity.ok(
                utilisateurService.createUtilisateur(utilisateur)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUtilisateurById(
            @PathVariable Integer id) {

        return utilisateurService.getUtilisateurById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    Map<String,Object> error = new HashMap<>();
                    error.put("message" , "alex");
                    error.put("id" , id);

                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {

        return ResponseEntity.ok(
                utilisateurService.getAllUtilisateurs()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(
            @PathVariable Integer id,
            @RequestBody Utilisateur utilisateur) {

        return ResponseEntity.ok(
                utilisateurService.updateUtilisateur(id, utilisateur)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(
            @PathVariable Integer id) {

        utilisateurService.deleteUtilisateur(id);

        return ResponseEntity.noContent().build();
    }
}