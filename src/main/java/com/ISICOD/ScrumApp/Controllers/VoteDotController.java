package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.VoteDot;
import com.ISICOD.ScrumApp.Services.VoteDotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vote-dots")
@RequiredArgsConstructor
public class VoteDotController {

    private final VoteDotService voteDotService;

    @PostMapping
    public ResponseEntity<VoteDot> createVoteDot(
            @RequestBody VoteDot voteDot) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(voteDotService.createVoteDot(voteDot));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVoteDotById(
            @PathVariable Integer id) {

        return voteDotService.getVoteDotById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {

                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "VoteDot not found");
                    error.put("id", id);

                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }

    @GetMapping
    public ResponseEntity<List<VoteDot>> getAllVoteDots() {

        return ResponseEntity.ok(
                voteDotService.getVoteDotAll()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VoteDot> updateVoteDot(
            @PathVariable Integer id,
            @RequestBody VoteDot voteDot) {

        return ResponseEntity.ok(
                voteDotService.updateVoteDot(id, voteDot)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoteDot(
            @PathVariable Integer id) {

        voteDotService.deleteVoteDot(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}