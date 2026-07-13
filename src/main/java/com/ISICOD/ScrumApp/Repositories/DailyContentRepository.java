package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.DailyContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyContentRepository extends JpaRepository<DailyContent, Integer> {
    List<DailyContent> findByParticipantSessionUtilisateurId(Integer utilisateurId);
}