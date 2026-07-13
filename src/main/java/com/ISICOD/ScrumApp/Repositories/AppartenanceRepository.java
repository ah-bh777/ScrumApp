package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.Appartenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppartenanceRepository extends JpaRepository<Appartenance, Integer> {

    List<Appartenance> getAppartenanceByUtilisateurId(Integer UtilisateurId);

    Optional<Appartenance> findByUtilisateurIdAndEspaceId(
            Integer utilisateurId,
            Integer espaceId
    );

}