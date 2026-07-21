package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.DTOs.Daily.DailySessionDTO;
import com.ISICOD.ScrumApp.DTOs.Poker.PokerSessionDTO;
import com.ISICOD.ScrumApp.DTOs.Retro.RetroSessionDTO;
import com.ISICOD.ScrumApp.DTOs.Session.SessionDetailsDTO;
import com.ISICOD.ScrumApp.Entities.Session;
import com.ISICOD.ScrumApp.Repositories.SessionRepository;
import com.ISICOD.ScrumApp.Services.Builders.RetroBuilder;
import com.ISICOD.ScrumApp.Services.DailyContentService;
import com.ISICOD.ScrumApp.Services.SessionService;
import com.ISICOD.ScrumApp.Services.VotePokerService;
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

    private final DailyContentService dailyContentService;

    private final VotePokerService votePokerService;


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

    @GetMapping("/{id}/details")
    public ResponseEntity<SessionDetailsDTO> getSessionDetails(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                sessionService.getSessionDetails(id)
        );
    }

    @GetMapping("/{sessionId}/daily")
    public ResponseEntity<DailySessionDTO> getDailySession(
            @PathVariable Integer sessionId) {

        return ResponseEntity.ok(
                dailyContentService.getDailySession(sessionId)
        );
    }

    @GetMapping("/{sessionId}/poker")
    public ResponseEntity<PokerSessionDTO> getPokerSession(
            @PathVariable Integer sessionId) {

        return ResponseEntity.ok(
                votePokerService.getPokerSession(sessionId)
        );
    }


}