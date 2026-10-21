
package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyStoryReservationDTO;
import com.ISICOD.ScrumApp.DTOs.DailyMeeting.DailyStoryReservationRequestDTO;
import com.ISICOD.ScrumApp.Services.DailyMeeting.DailyStoryReservationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daily")
@RequiredArgsConstructor
public class DailyStoryReservationController {

    private final DailyStoryReservationService reservationService;


    @PostMapping("/{sessionId}/reservations")
    public ResponseEntity<DailyStoryReservationDTO> reserveStory(
            @PathVariable Integer sessionId,
            @RequestBody DailyStoryReservationRequestDTO request) {

        DailyStoryReservationDTO reservation =
                reservationService.reserveStory(sessionId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservation);
    }

    @DeleteMapping("/{sessionId}/reservations")
    public ResponseEntity<Void> releaseStory(
            @PathVariable Integer sessionId,
            @RequestBody DailyStoryReservationRequestDTO request) {

        reservationService.releaseStory(sessionId, request);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{sessionId}/reservations")
    public ResponseEntity<List<DailyStoryReservationDTO>> getReservations(
            @PathVariable Integer sessionId) {

        return ResponseEntity.ok(
                reservationService.getReservations(sessionId)
        );
    }
}
