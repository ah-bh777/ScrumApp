package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.ParticipantSession;
import com.ISICOD.ScrumApp.Services.ParticipantSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/participant-sessions")
@RequiredArgsConstructor
public class ParticipantSessionController {

    private final ParticipantSessionService participantSessionService;


    @PostMapping
    public ResponseEntity<ParticipantSession> createParticipantSession(
            @RequestBody ParticipantSession participantSession) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        participantSessionService
                                .createParticipantSession(participantSession)
                );
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getParticipantSessionById(
            @PathVariable Integer id) {


        return participantSessionService
                .getParticipantSessionById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {

                    Map<String,Object> error = new HashMap<>();

                    error.put("message",
                            "ParticipantSession not found");

                    error.put("id", id);


                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }



    @GetMapping
    public ResponseEntity<List<ParticipantSession>> getAllParticipantSessions(){

        return ResponseEntity.ok(
                participantSessionService
                        .getParticipantSessionAll()
        );
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ParticipantSession> updateParticipantSession(
            @PathVariable Integer id,
            @RequestBody ParticipantSession participantSession) {


        return ResponseEntity.ok(
                participantSessionService
                        .updateParticipantSession(id, participantSession)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipantSession(
            @PathVariable Integer id) {


        participantSessionService
                .deleteParticipantSession(id);


        return ResponseEntity
                .noContent()
                .build();
    }
}