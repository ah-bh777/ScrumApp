package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.DailyContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyContentRepository extends JpaRepository<DailyContent, Integer> {
}