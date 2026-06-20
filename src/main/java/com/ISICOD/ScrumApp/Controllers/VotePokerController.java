package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.VotePoker;
import com.ISICOD.ScrumApp.Services.VotePokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vote-pokers")
@RequiredArgsConstructor
public class VotePokerController {

    private final VotePokerService votePokerService;


    @PostMapping
    public ResponseEntity<VotePoker> createVotePoker(
            @RequestBody VotePoker votePoker) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(votePokerService.createVotePoker(votePoker));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getVotePokerById(
            @PathVariable Integer id) {

        return votePokerService.getVotePokerById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {

                    Map<String,Object> error = new HashMap<>();
                    error.put("message", "VotePoker not found");
                    error.put("id", id);

                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }


    @GetMapping
    public ResponseEntity<List<VotePoker>> getAllVotePoker() {

        return ResponseEntity.ok(
                votePokerService.getVotePokerAll()
        );
    }


    @PatchMapping("/{id}")
    public ResponseEntity<VotePoker> updateVotePoker(
            @PathVariable Integer id,
            @RequestBody VotePoker votePoker) {

        return ResponseEntity.ok(
                votePokerService.updateVotePoker(id, votePoker)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVotePoker(
            @PathVariable Integer id) {

        votePokerService.deleteVotePoker(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}