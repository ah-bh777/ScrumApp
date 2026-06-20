package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.NoteRetro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRetroRepository extends JpaRepository<NoteRetro, Integer> {
}