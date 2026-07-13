package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.Appartenance;
import com.ISICOD.ScrumApp.Repositories.AppartenanceRepository;
import com.ISICOD.ScrumApp.Services.AppartenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppartenanceServiceImpl implements AppartenanceService {

    private final AppartenanceRepository appartenanceRepository;

    @Override
    public Appartenance createAppartenance(Appartenance appartenance) {
        return appartenanceRepository.save(appartenance);
    }

    @Override
    public Optional<Appartenance> getAppartenanceById(Integer id) {
        return appartenanceRepository.findById(id);
    }

    @Override
    public List<Appartenance> getAllAppartenances() {
        return appartenanceRepository.findAll();
    }

    @Override
    public Appartenance updateAppartenance(Integer id, Appartenance appartenance) {

        Appartenance existingAppartenance = appartenanceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Appartenance introuvable avec l'id : " + id));


        if (appartenance.getRejointA() != null) {
            existingAppartenance.setRejointA(appartenance.getRejointA());
        }

        if (appartenance.getRoleAttribue() != null) {
            existingAppartenance.setRoleAttribue(appartenance.getRoleAttribue());
        }

        if (appartenance.getUtilisateur() != null) {
            existingAppartenance.setUtilisateur(appartenance.getUtilisateur());
        }

        if (appartenance.getEspace() != null) {
            existingAppartenance.setEspace(appartenance.getEspace());
        }

        return appartenanceRepository.save(existingAppartenance);
    }

    @Override
    public void deleteAppartenance(Integer id) {

        Appartenance appartenance = appartenanceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Appartenance introuvable avec l'id : " + id));

        appartenanceRepository.delete(appartenance);
    }



}