package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.DailyContent;
import com.ISICOD.ScrumApp.Services.DailyContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/daily-content")
@RequiredArgsConstructor
public class DailyContentController {

    private final DailyContentService dailyContentService;


    @PostMapping
    public ResponseEntity<DailyContent> createDailyContent(
            @RequestBody DailyContent dailyContent) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dailyContentService.createDailyContent(dailyContent));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getDailyContentById(
            @PathVariable Integer id) {

        return dailyContentService.getDailyContentById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {

                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "DailyContent not found");
                    error.put("id", id);

                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }


    @GetMapping
    public ResponseEntity<List<DailyContent>> getAllDailyContents() {

        return ResponseEntity.ok(
                dailyContentService.getDailyContentAll()
        );
    }


    @PatchMapping("/{id}")
    public ResponseEntity<DailyContent> updateDailyContent(
            @PathVariable Integer id,
            @RequestBody DailyContent dailyContent) {

        return ResponseEntity.ok(
                dailyContentService.updateDailyContent(id, dailyContent)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDailyContent(
            @PathVariable Integer id) {

        dailyContentService.deleteDailyContent(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}