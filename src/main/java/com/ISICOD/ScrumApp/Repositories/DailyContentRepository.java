package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.DailyContent;
import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyContentRepository extends JpaRepository<DailyContent, Integer> {

    List<DailyContent> findByParticipantSessionUtilisateurId(Integer utilisateurId);

    List<DailyContent> findBySprintUserStorySprintIdAndTypeContenu(
            Integer sprintId,
            TypeDailyContent typeContenu
    );
}