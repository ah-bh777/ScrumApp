package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.SprintUserStory;
import com.ISICOD.ScrumApp.Services.SprintUserStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sprint-user-stories")
@RequiredArgsConstructor
public class SprintUserStoryController {

    private final SprintUserStoryService sprintUserStoryService;


    @PostMapping
    public ResponseEntity<SprintUserStory> createSprintUserStory(
            @RequestBody SprintUserStory sprintUserStory) {

        return ResponseEntity.ok(
                sprintUserStoryService.createSprintUserStory(sprintUserStory)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getSprintUserStoryById(
            @PathVariable Integer id) {


        return sprintUserStoryService.getSprintUserStoryById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)

                .orElseGet(() -> {

                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "SprintUserStory not found");
                    error.put("id", id);

                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }



    @GetMapping
    public ResponseEntity<List<SprintUserStory>> getAllSprintUserStories() {

        return ResponseEntity.ok(
                sprintUserStoryService.getAllSprintUserStories()
        );
    }



    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSprintUserStory(
            @PathVariable Integer id,
            @RequestBody SprintUserStory sprintUserStory) {


        try {

            return ResponseEntity.ok(
                    sprintUserStoryService.updateSprintUserStory(id, sprintUserStory)
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
    public ResponseEntity<?> deleteSprintUserStory(
            @PathVariable Integer id) {


        try {

            sprintUserStoryService.deleteSprintUserStory(id);

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