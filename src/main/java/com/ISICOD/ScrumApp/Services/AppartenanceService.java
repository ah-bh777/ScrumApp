package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.Appartenance;

import java.util.List;
import java.util.Optional;

public interface AppartenanceService {

    Appartenance createAppartenance(Appartenance appartenance);

    Optional<Appartenance> getAppartenanceById(Integer id);

    List<Appartenance> getAllAppartenances();

    Appartenance updateAppartenance(Integer id, Appartenance appartenance);

    void deleteAppartenance(Integer id);

}