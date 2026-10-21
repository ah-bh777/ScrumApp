package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.Notification;
import com.ISICOD.ScrumApp.Exceptions.ForbiddenException;
import com.ISICOD.ScrumApp.Exceptions.ResourceNotFoundException;
import com.ISICOD.ScrumApp.Repositories.NotificationRepository;
import com.ISICOD.ScrumApp.Services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> getNotificationById(Integer id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public void deleteNotification(Integer id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification introuvable avec l'id : " + id));

        notificationRepository.delete(notification);
    }

    @Override
    public Notification updateNotification(Integer id, Notification notification) {
        Notification existing = notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification introuvable avec l'id : " + id));

        if (notification.getDescription() != null) {
            existing.setDescription(notification.getDescription());
        }

        if (notification.getEnvoyeA() != null) {
            existing.setEnvoyeA(notification.getEnvoyeA());
        }

        if (notification.getLuA() != null) {
            existing.setLuA(notification.getLuA());
        }

        if (notification.getUtilisateur() != null) {
            existing.setUtilisateur(notification.getUtilisateur());
        }

        if (notification.getActionItem() != null) {
            existing.setActionItem(notification.getActionItem());
        }

        return notificationRepository.save(existing);
    }

    @Override
    @Transactional
    public Notification markAsRead(Integer notificationId, Integer utilisateurId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification introuvable : " + notificationId
                ));

        if (utilisateurId == null
                || notification.getUtilisateur() == null
                || !Objects.equals(notification.getUtilisateur().getId(), utilisateurId)) {
            throw new ForbiddenException(
                    "Vous ne pouvez pas modifier cette notification."
            );
        }

        if (notification.getLuA() == null) {
            notification.setLuA(LocalDateTime.now());
            notificationRepository.save(notification);
        }

        return notification;
    }

    @Override
    @Transactional
    public int markAllAsRead(Integer utilisateurId) {
        if (utilisateurId == null) {
            throw new ForbiddenException("Utilisateur requis.");
        }

        List<Notification> notifications =
                notificationRepository.findByUtilisateurId(utilisateurId);

        LocalDateTime now = LocalDateTime.now();

        List<Notification> unread = notifications.stream()
                .filter(notification -> notification.getLuA() == null)
                .toList();

        unread.forEach(notification -> notification.setLuA(now));

        notificationRepository.saveAll(unread);

        return unread.size();
    }
}