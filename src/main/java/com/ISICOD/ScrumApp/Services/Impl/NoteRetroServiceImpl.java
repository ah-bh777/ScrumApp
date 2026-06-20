package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.NoteRetro;
import com.ISICOD.ScrumApp.Repositories.NoteRetroRepository;
import com.ISICOD.ScrumApp.Services.NoteRetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteRetroServiceImpl implements NoteRetroService {

    private final NoteRetroRepository noteRetroRepository;

    @Override
    public NoteRetro createNoteRetro(NoteRetro noteRetro) {
        return noteRetroRepository.save(noteRetro);
    }

    @Override
    public Optional<NoteRetro> getNoteRetroById(Integer id) {
        return noteRetroRepository.findById(id);
    }

    @Override
    public List<NoteRetro> getNoteRetroAll() {
        return noteRetroRepository.findAll();
    }

    @Override
    public void deleteNoteRetro(Integer id) {

        NoteRetro existing = noteRetroRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("NoteRetro introuvable avec id : " + id));

        noteRetroRepository.delete(existing);
    }


    @Override
    public NoteRetro updateNoteRetro(Integer id, NoteRetro noteRetro) {

        NoteRetro existing = noteRetroRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("NoteRetro introuvable avec id : " + id));



        if (noteRetro.getTypeColonne() != null) {
            existing.setTypeColonne(noteRetro.getTypeColonne());
        }

        if (noteRetro.getContenu() != null) {
            existing.setContenu(noteRetro.getContenu());
        }

        if (noteRetro.getCreeA() != null) {
            existing.setCreeA(noteRetro.getCreeA());
        }

        if (noteRetro.getGroupeNote() != null) {
            existing.setGroupeNote(noteRetro.getGroupeNote());
        }

        if (noteRetro.getUtilisateur() != null) {
            existing.setUtilisateur(noteRetro.getUtilisateur());
        }

        return noteRetroRepository.save(existing);
    }
}