package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.Appartenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppartenanceRepository extends JpaRepository<Appartenance, Integer> {
}