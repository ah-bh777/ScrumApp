package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.NoteRetro;
import com.ISICOD.ScrumApp.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRetroRepository extends JpaRepository<NoteRetro, Integer> {

    List<NoteRetro> findByUtilisateurId(Integer utilisateurId);

}