package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.TypeSession;
import com.ISICOD.ScrumApp.Services.TypeSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/type-sessions")
@RequiredArgsConstructor
public class TypeSessionController {


    private final TypeSessionService typeSessionService;



    @PostMapping
    public ResponseEntity<TypeSession> createTypeSession(
            @RequestBody TypeSession typeSession) {


        return ResponseEntity.ok(
                typeSessionService.createTypeSession(typeSession)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTypeSessionById(
            @PathVariable Integer id) {


        return typeSessionService.getTypeSessionById(id)

                .<ResponseEntity<?>>map(ResponseEntity::ok)

                .orElseGet(() -> {


                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "TypeSession not found");
                    error.put("id", id);


                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);

                });
    }


    @GetMapping
    public ResponseEntity<List<TypeSession>> getAllTypeSessions() {


        return ResponseEntity.ok(
                typeSessionService.getAllTypeSessions()
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTypeSession(
            @PathVariable Integer id) {


        try {


            typeSessionService.deleteTypeSession(id);


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