package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.SessionConfiguration;
import com.ISICOD.ScrumApp.Services.SessionConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/session-configurations")
@RequiredArgsConstructor
public class SessionConfigurationController {

    private final SessionConfigurationService service;


    @PostMapping
    public ResponseEntity<SessionConfiguration> create(
            @RequestBody SessionConfiguration config) {


        return ResponseEntity.ok(
                service.create(config)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Integer id) {


        return service.getById(id)

                .<ResponseEntity<?>>map(ResponseEntity::ok)

                .orElseGet(() -> {


                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "SessionConfiguration introuvable");
                    error.put("id", id);


                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);

                });
    }


    @GetMapping
    public ResponseEntity<List<SessionConfiguration>> getAll() {


        return ResponseEntity.ok(
                service.getAll()
        );
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody SessionConfiguration config) {


        try {


            return ResponseEntity.ok(
                    service.update(id, config)
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