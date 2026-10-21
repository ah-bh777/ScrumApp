package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUtilisateurId(Integer utilisateurId);

    List<Notification> findByUtilisateurIdOrderByEnvoyeADesc(Integer utilisateurId);

    long countByUtilisateurIdAndLuAIsNull(Integer utilisateurId);
}