package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.NoteRetro;

import java.util.List;
import java.util.Optional;

public interface NoteRetroService {

    NoteRetro createNoteRetro(NoteRetro noteRetro);

    Optional<NoteRetro> getNoteRetroById(Integer id);

    List<NoteRetro> getNoteRetroAll();

    NoteRetro updateNoteRetro(Integer id, NoteRetro noteRetro);

    void deleteNoteRetro(Integer id);
}