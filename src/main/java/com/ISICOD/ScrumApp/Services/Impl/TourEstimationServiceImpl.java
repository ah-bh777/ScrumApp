package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.TourEstimation;
import com.ISICOD.ScrumApp.Repositories.TourEstimationRepository;
import com.ISICOD.ScrumApp.Services.TourEstimationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourEstimationServiceImpl implements TourEstimationService {

    private final TourEstimationRepository tourEstimationRepository;

    @Override
    public TourEstimation createTourEstimation(TourEstimation tourEstimation) {
        return tourEstimationRepository.save(tourEstimation);
    }

    @Override
    public Optional<TourEstimation> getTourEstimationById(Integer id) {
        return tourEstimationRepository.findById(id);
    }

    @Override
    public List<TourEstimation> getTourEstimationAll() {
        return tourEstimationRepository.findAll();
    }

    @Override
    public TourEstimation updateTourEstimation(Integer id, TourEstimation tourEstimation) {

        TourEstimation existing = tourEstimationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("TourEstimation introuvable avec id : " + id));

        if (tourEstimation.getNumeroTour() != null) {
            existing.setNumeroTour(tourEstimation.getNumeroTour());
        }

        if (tourEstimation.getRevele() != null) {
            existing.setRevele(tourEstimation.getRevele());
        }

        if (tourEstimation.getValeurFinale() != null) {
            existing.setValeurFinale(tourEstimation.getValeurFinale());
        }

        if (tourEstimation.getCreeA() != null) {
            existing.setCreeA(tourEstimation.getCreeA());
        }

        if (tourEstimation.getSelectionUserStorySession() != null) {
            existing.setSelectionUserStorySession(
                    tourEstimation.getSelectionUserStorySession()
            );
        }

        return tourEstimationRepository.save(existing);
    }

    @Override
    public void deleteTourEstimation(Integer id) {

        TourEstimation tourEstimation = tourEstimationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "TourEstimation introuvable avec id : " + id));

        tourEstimationRepository.delete(tourEstimation);
    }
}