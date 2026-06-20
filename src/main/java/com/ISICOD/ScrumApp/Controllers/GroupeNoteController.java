package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.GroupeNote;
import com.ISICOD.ScrumApp.Services.GroupeNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groupe-notes")
@RequiredArgsConstructor
public class GroupeNoteController {

    private final GroupeNoteService groupeNoteService;

    @PostMapping
    public ResponseEntity<GroupeNote> createGroupeNote(
            @RequestBody GroupeNote groupeNote) {


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        groupeNoteService.createGroupeNote(groupeNote)
                );
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupeNoteById(
            @PathVariable Integer id) {


        return groupeNoteService
                .getGroupeNoteById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {


                    Map<String,Object> error = new HashMap<>();

                    error.put("message",
                            "GroupeNote not found");

                    error.put("id", id);


                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);

                });
    }



    @GetMapping
    public ResponseEntity<List<GroupeNote>> getAllGroupeNotes(){


        return ResponseEntity.ok(
                groupeNoteService.getGroupeNoteAll()
        );
    }



    @PatchMapping("/{id}")
    public ResponseEntity<GroupeNote> updateGroupeNote(
            @PathVariable Integer id,
            @RequestBody GroupeNote groupeNote) {


        return ResponseEntity.ok(
                groupeNoteService
                        .updateGroupeNote(id, groupeNote)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupeNote(
            @PathVariable Integer id) {

        groupeNoteService.deleteGroupeNote(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}