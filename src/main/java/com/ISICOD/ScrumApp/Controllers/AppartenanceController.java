package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Services.AppartenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appartenances")
@RequiredArgsConstructor
public class AppartenanceController {

    private final AppartenanceService appartenanceService;

    @PostMapping
    public ResponseEntity<Appartenance> createAppartenance(
            @RequestBody Appartenance appartenance) {

        return ResponseEntity.ok(
                appartenanceService.createAppartenance(appartenance)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appartenance> getAppartenanceById(
            @PathVariable Integer id) {

        return appartenanceService.getAppartenanceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Appartenance>> getAllAppartenances() {

        return ResponseEntity.ok(
                appartenanceService.getAllAppartenances()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Appartenance> updateAppartenance(
            @PathVariable Integer id,
            @RequestBody Appartenance appartenance) {

        return ResponseEntity.ok(
                appartenanceService.updateAppartenance(id, appartenance)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppartenance(
            @PathVariable Integer id) {

        appartenanceService.deleteAppartenance(id);

        return ResponseEntity.noContent().build();
    }
}