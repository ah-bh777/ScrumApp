package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}