package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.DTOs.Sprint.SprintDetailsDTO;
import com.ISICOD.ScrumApp.Entities.Sprint;
import com.ISICOD.ScrumApp.Services.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;


    @PostMapping
    public ResponseEntity<Sprint> createSprint(
            @RequestBody Sprint sprint) {

        return ResponseEntity.ok(
                sprintService.createSprint(sprint)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getSprintById(
            @PathVariable Integer id) {


        return sprintService.getSprintById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)

                .orElseGet(() -> {

                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "Sprint not found");
                    error.put("id", id);

                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }


    @GetMapping
    public ResponseEntity<List<Sprint>> getAllSprints() {

        return ResponseEntity.ok(
                sprintService.getAllSprints()
        );
    }



    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSprint(
            @PathVariable Integer id,
            @RequestBody Sprint sprint) {


        try {

            return ResponseEntity.ok(
                    sprintService.updateSprint(id, sprint)
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
    public ResponseEntity<?> deleteSprint(
            @PathVariable Integer id) {


        try {

            sprintService.deleteSprint(id);

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

    @GetMapping("/{id}/details")
    public ResponseEntity<SprintDetailsDTO> getSprintDetails(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                sprintService.getSprintDetails(id)
        );
    }
}