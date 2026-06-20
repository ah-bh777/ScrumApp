package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.TourEstimation;

import java.util.List;
import java.util.Optional;

public interface TourEstimationService {

    TourEstimation createTourEstimation(TourEstimation tourEstimation);

    Optional<TourEstimation> getTourEstimationById(Integer id);

    List<TourEstimation> getTourEstimationAll();

    TourEstimation updateTourEstimation(Integer id, TourEstimation tourEstimation);

    void deleteTourEstimation(Integer id);
}
