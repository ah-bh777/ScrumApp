package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    Notification createNotification(Notification notification);

    Optional<Notification> getNotificationById(Integer id);

    List<Notification> getAllNotifications();

    Notification updateNotification(Integer id, Notification notification);

    void deleteNotification(Integer id);
}