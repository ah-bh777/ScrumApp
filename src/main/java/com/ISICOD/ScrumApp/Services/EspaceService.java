package com.ISICOD.ScrumApp.Services;


import com.ISICOD.ScrumApp.DTOs.Espace.*;
import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.DTOs.Espace.LeaveWorkspaceRequest;

import java.util.List;
import java.util.Optional;

public interface EspaceService {

    Espace createEspace(Espace espace);

    Optional<Espace> getEspaceById(Integer id);

    List<Espace> getAllEspaces();

    Espace updateEspace(Integer id, Espace espace);

    void deleteEspace(Integer id);

    EspaceDashboardDTO getDashboard(Integer espaceId);

    List<EspaceMemberListDTO> getEspaceMembers(
            Integer espaceId
    );
    CreateEspaceResponse createEspaceWithScrumMaster(
            CreateEspaceRequest request
    );

    List<UserEspaceDTO> getEspacesByUtilisateur(Integer utilisateurId);

    void changerRoleMembre(
            Integer espaceId,
            Integer membreId,
            ChangeMemberRoleRequest request
    );

    void supprimerMembre(
            Integer espaceId,
            Integer membreId,
            RemoveMemberRequest request
    );

    void quitterEspace(
            Integer espaceId,
            LeaveWorkspaceRequest request
    );

    void transfererScrumMaster(
            Integer espaceId,
            TransferScrumMasterRequest request
    );
}