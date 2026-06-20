package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.Notification;
import com.ISICOD.ScrumApp.Services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    // CREATE
    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @RequestBody Notification notification) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificationService.createNotification(notification));
    }


    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getNotificationById(
            @PathVariable Integer id) {


        return notificationService.getNotificationById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {

                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "Notification not found");
                    error.put("id", id);


                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }


    // GET ALL
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {

        return ResponseEntity.ok(
                notificationService.getAllNotifications()
        );
    }


    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(
            @PathVariable Integer id,
            @RequestBody Notification notification) {


        return ResponseEntity.ok(
                notificationService.updateNotification(id, notification)
        );
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Integer id) {


        notificationService.deleteNotification(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}