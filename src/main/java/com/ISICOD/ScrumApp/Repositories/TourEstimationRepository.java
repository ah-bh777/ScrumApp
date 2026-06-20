package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.TourEstimation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourEstimationRepository extends JpaRepository<TourEstimation, Integer> {
}