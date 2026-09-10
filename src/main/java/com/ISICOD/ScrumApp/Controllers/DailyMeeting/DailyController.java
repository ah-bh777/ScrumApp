package com.ISICOD.ScrumApp.Controllers.DailyMeeting;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyContentRequestDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyContentUpdateDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailySessionRequestDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyStoryOptionDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.ParticipantJoinRequestDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.SessionStatusUpdateDTO;
import com.ISICOD.ScrumApp.Services.DailyMeeting.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daily")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DailyController {

    private final DailyService dailyService;

    // ============================================================
    // GET DAILY
    // ============================================================

    @GetMapping("/{sessionId}")
    public ResponseEntity<DailyDTO> getDaily(
            @PathVariable Integer sessionId
    ) {

        return ResponseEntity.ok(
                dailyService.getDaily(sessionId)
        );
    }

    // ============================================================
    // GET STORIES OF A SPRINT
    // ============================================================

    @GetMapping("/sprints/{sprintId}/stories")
    public ResponseEntity<List<DailyStoryOptionDTO>> getDailyStories(
            @PathVariable Integer sprintId
    ) {

        return ResponseEntity.ok(
                dailyService.getDailyStories(sprintId)
        );
    }

    // ============================================================
    // CREATE DAILY
    // ============================================================

    @PostMapping("/workspaces/{espaceId}/sprints/{sprintId}")
    public ResponseEntity<DailyDTO> createDaily(
            @PathVariable Integer espaceId,
            @PathVariable Integer sprintId,
            @RequestParam Integer utilisateurId,
            @RequestBody DailySessionRequestDTO request
    ) {

        DailyDTO daily = dailyService.createDaily(
                espaceId,
                sprintId,
                utilisateurId,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(daily);
    }

    // ============================================================
    // JOIN DAILY
    // ============================================================

    @PostMapping("/{sessionId}/participants")
    public ResponseEntity<DailyDTO> joinDaily(
            @PathVariable Integer sessionId,
            @RequestBody ParticipantJoinRequestDTO request
    ) {

        return ResponseEntity.ok(
                dailyService.joinDaily(
                        sessionId,
                        request
                )
        );
    }

    // ============================================================
    // ADD CONTENT
    // ============================================================

    @PostMapping("/{sessionId}/contents")
    public ResponseEntity<DailyDTO> addContent(
            @PathVariable Integer sessionId,
            @RequestBody DailyContentRequestDTO request
    ) {

        return ResponseEntity.ok(
                dailyService.addContent(
                        sessionId,
                        request
                )
        );
    }

    // ============================================================
    // UPDATE CONTENT
    // ============================================================

    @PutMapping("/{sessionId}/contents/{dailyContentId}")
    public ResponseEntity<DailyDTO> updateContent(
            @PathVariable Integer sessionId,
            @PathVariable Integer dailyContentId,
            @RequestBody DailyContentUpdateDTO request
    ) {

        return ResponseEntity.ok(
                dailyService.updateContent(
                        sessionId,
                        dailyContentId,
                        request
                )
        );
    }

    // ============================================================
    // DELETE CONTENT
    // ============================================================

    @DeleteMapping("/{sessionId}/contents/{dailyContentId}")
    public ResponseEntity<Void> deleteContent(
            @PathVariable Integer sessionId,
            @PathVariable Integer dailyContentId,
            @RequestParam Integer utilisateurId
    ) {

        dailyService.deleteContent(
                sessionId,
                dailyContentId,
                utilisateurId
        );

        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // UPDATE SESSION STATUS
    // ============================================================

    @PatchMapping("/{sessionId}/status")
    public ResponseEntity<DailyDTO> updateSessionStatus(
            @PathVariable Integer sessionId,
            @RequestParam Integer utilisateurId,
            @RequestBody SessionStatusUpdateDTO request
    ) {

        return ResponseEntity.ok(
                dailyService.updateSessionStatus(
                        sessionId,
                        utilisateurId,
                        request
                )
        );
    }
}