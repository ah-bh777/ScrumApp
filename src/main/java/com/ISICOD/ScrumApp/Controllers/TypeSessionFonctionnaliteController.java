package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.TypeSessionFonctionnalite;
import com.ISICOD.ScrumApp.Services.TypeSessionFonctionnaliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/type-session-fonctionnalites")
@RequiredArgsConstructor
public class TypeSessionFonctionnaliteController {


    private final TypeSessionFonctionnaliteService service;



    @PostMapping
    public ResponseEntity<TypeSessionFonctionnalite> create(
            @RequestBody TypeSessionFonctionnalite entity) {


        return ResponseEntity.ok(
                service.create(entity)
        );
    }





    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Integer id) {


        return service.getById(id)

                .<ResponseEntity<?>>map(ResponseEntity::ok)

                .orElseGet(() -> {


                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "TypeSessionFonctionnalite not found");
                    error.put("id", id);


                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);

                });
    }





    @GetMapping
    public ResponseEntity<List<TypeSessionFonctionnalite>> getAll() {


        return ResponseEntity.ok(
                service.getAll()
        );
    }






    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Integer id) {


        try {


            service.delete(id);


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