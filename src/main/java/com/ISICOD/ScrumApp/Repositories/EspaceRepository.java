package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.Espace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspaceRepository extends JpaRepository<Espace, Integer> {
}