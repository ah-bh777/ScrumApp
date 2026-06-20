package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.EtatRetro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatRetroRepository extends JpaRepository<EtatRetro, Integer> {
}