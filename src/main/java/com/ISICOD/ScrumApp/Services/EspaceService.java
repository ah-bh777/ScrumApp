package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.DTOs.Espace.EspaceDashboardDTO;
import com.ISICOD.ScrumApp.Entities.Espace;

import java.util.List;
import java.util.Optional;

public interface EspaceService {

    Espace createEspace(Espace espace);

    Optional<Espace> getEspaceById(Integer id);

    List<Espace> getAllEspaces();

    Espace updateEspace(Integer id, Espace espace);

    void deleteEspace(Integer id);

    EspaceDashboardDTO getDashboard(Integer espaceId);
}