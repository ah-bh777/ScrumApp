package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.DTOs.Espace.EspaceDashboardDTO;
import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Services.EspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/espaces")
@RequiredArgsConstructor
public class EspaceController {

    private final EspaceService espaceService;

    @PostMapping
    public ResponseEntity<Espace> createEspace(
            @RequestBody Espace espace) {

        return ResponseEntity.ok(
                espaceService.createEspace(espace)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Espace> getEspaceById(
            @PathVariable Integer id) {

        return espaceService.getEspaceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Espace>> getAllEspaces() {

        return ResponseEntity.ok(
                espaceService.getAllEspaces()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Espace> updateEspace(
            @PathVariable Integer id,
            @RequestBody Espace espace) {

        return ResponseEntity.ok(
                espaceService.updateEspace(id, espace)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspace(
            @PathVariable Integer id) {

        espaceService.deleteEspace(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<EspaceDashboardDTO> getDashboard(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                espaceService.getDashboard(id)
        );
    }
}