package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.GroupeNote;

import java.util.List;
import java.util.Optional;

public interface GroupeNoteService {

    GroupeNote createGroupeNote(GroupeNote groupeNote);

    Optional<GroupeNote> getGroupeNoteById(Integer id);

    List<GroupeNote> getGroupeNoteAll();

    GroupeNote updateGroupeNote(Integer id, GroupeNote groupeNote);

    void deleteGroupeNote(Integer id);
}