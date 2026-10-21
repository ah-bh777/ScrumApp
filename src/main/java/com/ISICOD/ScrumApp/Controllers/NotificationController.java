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

    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @RequestBody Notification notification) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificationService.createNotification(notification));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotificationById(@PathVariable Integer id) {
        return notificationService.getNotificationById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("message", "Notification not found");
                    error.put("id", id);

                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(
            @PathVariable Integer id,
            @RequestBody Notification notification) {

        return ResponseEntity.ok(
                notificationService.updateNotification(id, notification)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Integer id) {
        notificationService.deleteNotification(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Map<String, Object>> markAsRead(
            @PathVariable Integer notificationId,
            @RequestParam Integer utilisateurId) {

        Notification notification =
                notificationService.markAsRead(notificationId, utilisateurId);

        Map<String, Object> response = new HashMap<>();
        response.put("notificationId", notification.getId());
        response.put("description", notification.getDescription());
        response.put("envoyeA", notification.getEnvoyeA());
        response.put("luA", notification.getLuA());
        response.put("invitationId", notification.getInvitation() != null
                ? notification.getInvitation().getId() : null);
        response.put("actionItemId", notification.getActionItem() != null
                ? notification.getActionItem().getId() : null);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/read-all")
    public ResponseEntity<Map<String, Object>> markAllAsRead(
            @RequestParam Integer utilisateurId) {

        int updated = notificationService.markAllAsRead(utilisateurId);

        return ResponseEntity.ok(Map.of(
                "updated", updated
        ));
    }
}