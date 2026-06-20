package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.Notification;
import com.ISICOD.ScrumApp.Repositories.NotificationRepository;
import com.ISICOD.ScrumApp.Services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
}