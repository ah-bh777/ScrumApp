package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.Espace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EspaceRepository extends JpaRepository<Espace, Integer> {
    Optional<Espace> findById(Integer id);
}