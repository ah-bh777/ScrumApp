package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.GroupeNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupeNoteRepository extends JpaRepository<GroupeNote, Integer> {
}