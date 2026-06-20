package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.SelectionUserStorySession;
import com.ISICOD.ScrumApp.Services.SelectionUserStorySessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/selection-user-story-sessions")
@RequiredArgsConstructor
public class SelectionUserStorySessionController {

    private final SelectionUserStorySessionService service;

    @PostMapping
    public ResponseEntity<SelectionUserStorySession> createSelection(
            @RequestBody SelectionUserStorySession selection) {

        return ResponseEntity.ok(
                service.createSelection(selection)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSelectionById(
            @PathVariable Integer id) {

        return service.getSelectionById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {

                    Map<String, Object> error = new HashMap<>();
                    error.put("message",
                            "SelectionUserStorySession introuvable");
                    error.put("id", id);

                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }

    @GetMapping
    public ResponseEntity<List<SelectionUserStorySession>>
    getAllSelections() {

        return ResponseEntity.ok(
                service.getAllSelections()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSelection(
            @PathVariable Integer id,
            @RequestBody SelectionUserStorySession selection) {

        try {

            return ResponseEntity.ok(
                    service.updateSelection(id, selection)
            );

        } catch (RuntimeException e) {

            Map<String, Object> error = new HashMap<>();
            error.put("message", e.getMessage());
            error.put("id", id);

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSelection(
            @PathVariable Integer id) {

        try {

            service.deleteSelection(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {

            Map<String, Object> error = new HashMap<>();
            error.put("message", e.getMessage());
            error.put("id", id);

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(error);
        }
    }
}