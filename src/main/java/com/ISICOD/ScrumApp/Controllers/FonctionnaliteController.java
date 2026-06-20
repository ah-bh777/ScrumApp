package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.Fonctionnalite;
import com.ISICOD.ScrumApp.Services.FonctionnaliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fonctionnalites")
@RequiredArgsConstructor
public class FonctionnaliteController {


    private final FonctionnaliteService fonctionnaliteService;



    @PostMapping
    public ResponseEntity<Fonctionnalite> createFonctionnalite(
            @RequestBody Fonctionnalite fonctionnalite) {


        return ResponseEntity.ok(
                fonctionnaliteService.createFonctionnalite(fonctionnalite)
        );
    }





    @GetMapping("/{id}")
    public ResponseEntity<?> getFonctionnaliteById(
            @PathVariable Integer id) {


        return fonctionnaliteService.getFonctionnaliteById(id)

                .<ResponseEntity<?>>map(ResponseEntity::ok)

                .orElseGet(() -> {


                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "Fonctionnalite not found");
                    error.put("id", id);


                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);

                });
    }






    @GetMapping
    public ResponseEntity<List<Fonctionnalite>> getAllFonctionnalites() {


        return ResponseEntity.ok(
                fonctionnaliteService.getAllFonctionnalites()
        );
    }






    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFonctionnalite(
            @PathVariable Integer id) {


        try {


            fonctionnaliteService.deleteFonctionnalite(id);


            return ResponseEntity.noContent().build();



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