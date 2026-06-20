package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.NoteRetro;
import com.ISICOD.ScrumApp.Services.NoteRetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/note-retros")
@RequiredArgsConstructor
public class NoteRetroController {

    private final NoteRetroService noteRetroService;


    @PostMapping
    public ResponseEntity<NoteRetro> createNoteRetro(
            @RequestBody NoteRetro noteRetro) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(noteRetroService.createNoteRetro(noteRetro));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteRetroById(
            @PathVariable Integer id) {

        return noteRetroService.getNoteRetroById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {

                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "NoteRetro not found");
                    error.put("id", id);

                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }


    @GetMapping
    public ResponseEntity<List<NoteRetro>> getAllNoteRetros() {

        return ResponseEntity.ok(
                noteRetroService.getNoteRetroAll()
        );
    }


    @PatchMapping("/{id}")
    public ResponseEntity<NoteRetro> updateNoteRetro(
            @PathVariable Integer id,
            @RequestBody NoteRetro noteRetro) {

        return ResponseEntity.ok(
                noteRetroService.updateNoteRetro(id, noteRetro)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteRetro(
            @PathVariable Integer id) {

        noteRetroService.deleteNoteRetro(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}