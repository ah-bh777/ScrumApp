package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.Session;
import com.ISICOD.ScrumApp.Services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<Session> createSession(
            @RequestBody Session session) {


        return ResponseEntity.ok(
                sessionService.createSession(session)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSessionById(
            @PathVariable Integer id) {

        return sessionService.getSessionById(id)

                .<ResponseEntity<?>>map(ResponseEntity::ok)

                .orElseGet(() -> {


                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "Session introuvable");
                    error.put("id", id);


                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);

                });
    }

    @GetMapping
    public ResponseEntity<List<Session>> getAllSessions() {


        return ResponseEntity.ok(
                sessionService.getAllSessions()
        );
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSession(
            @PathVariable Integer id,
            @RequestBody Session session) {

        try {


            return ResponseEntity.ok(
                    sessionService.updateSession(id, session)
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
    public ResponseEntity<?> deleteSession(
            @PathVariable Integer id) {

        try {

            sessionService.deleteSession(id);

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