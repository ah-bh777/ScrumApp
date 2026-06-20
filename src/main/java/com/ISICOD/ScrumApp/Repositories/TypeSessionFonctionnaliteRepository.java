package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.TypeSessionFonctionnalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeSessionFonctionnaliteRepository extends JpaRepository<TypeSessionFonctionnalite, Integer> {
}