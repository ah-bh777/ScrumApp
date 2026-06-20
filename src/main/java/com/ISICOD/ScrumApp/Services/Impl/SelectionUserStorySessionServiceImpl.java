package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.SelectionUserStorySession;
import com.ISICOD.ScrumApp.Repositories.SelectionUserStorySessionRepository;
import com.ISICOD.ScrumApp.Services.SelectionUserStorySessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SelectionUserStorySessionServiceImpl
        implements SelectionUserStorySessionService {

    private final SelectionUserStorySessionRepository repository;

    @Override
    public SelectionUserStorySession createSelection(
            SelectionUserStorySession selection) {

        return repository.save(selection);
    }

    @Override
    public Optional<SelectionUserStorySession> getSelectionById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<SelectionUserStorySession> getAllSelections() {
        return repository.findAll();
    }

    @Override
    public SelectionUserStorySession updateSelection(
            Integer id,
            SelectionUserStorySession selection) {

        SelectionUserStorySession existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "SelectionUserStorySession introuvable avec id : " + id));

        if (selection.getSelectionneA() != null) {
            existing.setSelectionneA(selection.getSelectionneA());
        }

        if (selection.getSession() != null) {
            existing.setSession(selection.getSession());
        }

        if (selection.getUserStory() != null) {
            existing.setUserStory(selection.getUserStory());
        }

        return repository.save(existing);
    }

    @Override
    public void deleteSelection(Integer id) {

        SelectionUserStorySession selection = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "SelectionUserStorySession introuvable avec id : " + id));

        repository.delete(selection);
    }

}