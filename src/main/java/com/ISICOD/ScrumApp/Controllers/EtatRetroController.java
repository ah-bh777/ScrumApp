package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.EtatRetro;
import com.ISICOD.ScrumApp.Services.EtatRetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/etat-retros")
@RequiredArgsConstructor
public class EtatRetroController {

    private final EtatRetroService service;

    @PostMapping
    public ResponseEntity<EtatRetro> create(
            @RequestBody EtatRetro etatRetro) {


        return ResponseEntity.ok(
                service.create(etatRetro)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Integer id) {


        return service.getById(id)

                .<ResponseEntity<?>>map(ResponseEntity::ok)

                .orElseGet(() -> {


                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "EtatRetro introuvable");
                    error.put("id", id);


                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);

                });
    }







    @GetMapping
    public ResponseEntity<List<EtatRetro>> getAll() {


        return ResponseEntity.ok(
                service.getAll()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody EtatRetro etatRetro) {

        try {

            return ResponseEntity.ok(
                    service.update(id, etatRetro)
            );

        } catch (RuntimeException e) {


            Map<String,Object> error = new HashMap<>();

            error.put("message", e.getMessage());
            error.put("id", id);


            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(error);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Integer id) {

        try {

            service.delete(id);

            return ResponseEntity
                    .noContent()
                    .build();

        } catch (RuntimeException e) {

            Map<String,Object> error = new HashMap<>();

            error.put("message", e.getMessage());
            error.put("id", id);

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(error);
        }
    }

}