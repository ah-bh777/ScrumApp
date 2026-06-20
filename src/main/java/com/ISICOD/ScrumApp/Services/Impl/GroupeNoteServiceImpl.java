package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.GroupeNote;
import com.ISICOD.ScrumApp.Repositories.GroupeNoteRepository;
import com.ISICOD.ScrumApp.Services.GroupeNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupeNoteServiceImpl implements GroupeNoteService {

    private final GroupeNoteRepository groupeNoteRepository;

    @Override
    public GroupeNote createGroupeNote(GroupeNote groupeNote) {
        return groupeNoteRepository.save(groupeNote);
    }

    @Override
    public Optional<GroupeNote> getGroupeNoteById(Integer id) {
        return groupeNoteRepository.findById(id);
    }

    @Override
    public List<GroupeNote> getGroupeNoteAll() {
        return groupeNoteRepository.findAll();
    }

    @Override
    public GroupeNote updateGroupeNote(Integer id, GroupeNote groupeNote) {

        GroupeNote existing = groupeNoteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("GroupeNote introuvable avec id : " + id));

        if (groupeNote.getLabel() != null) {
            existing.setLabel(groupeNote.getLabel());
        }

        if (groupeNote.getSession() != null) {
            existing.setSession(groupeNote.getSession());
        }

        return groupeNoteRepository.save(existing);
    }

    @Override
    public void deleteGroupeNote(Integer id) {

        GroupeNote existing = groupeNoteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("GroupeNote introuvable avec id : " + id));

        groupeNoteRepository.delete(existing);
    }
}