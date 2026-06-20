package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.TourEstimation;
import com.ISICOD.ScrumApp.Services.TourEstimationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tour-estimations")
@RequiredArgsConstructor
public class TourEstimationController {

    private final TourEstimationService tourEstimationService;

    @PostMapping
    public ResponseEntity<TourEstimation> createTourEstimation(
            @RequestBody TourEstimation tourEstimation) {

        return ResponseEntity.ok(
                tourEstimationService.createTourEstimation(tourEstimation)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTourEstimationById(
            @PathVariable Integer id) {

        return tourEstimationService.getTourEstimationById(id)

                .<ResponseEntity<?>>map(ResponseEntity::ok)

                .orElseGet(() -> {

                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "TourEstimation introuvable");
                    error.put("id", id);


                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }

    @GetMapping
    public ResponseEntity<List<TourEstimation>> getAllTourEstimations() {


        return ResponseEntity.ok(
                tourEstimationService.getTourEstimationAll()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTourEstimation(
            @PathVariable Integer id,
            @RequestBody TourEstimation tourEstimation) {

        try {


            return ResponseEntity.ok(
                    tourEstimationService.updateTourEstimation(id, tourEstimation)
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
    public ResponseEntity<?> deleteTourEstimation(
            @PathVariable Integer id) {

        try {


            tourEstimationService.deleteTourEstimation(id);


            return ResponseEntity
                    .noContent()
                    .build();



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