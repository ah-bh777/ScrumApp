package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.SprintUserStory;
import com.ISICOD.ScrumApp.Repositories.SprintUserStoryRepository;
import com.ISICOD.ScrumApp.Services.SprintUserStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SprintUserStoryServiceImpl implements SprintUserStoryService {

    private final SprintUserStoryRepository sprintUserStoryRepository;

    @Override
    public SprintUserStory createSprintUserStory(SprintUserStory sprintUserStory) {
        return sprintUserStoryRepository.save(sprintUserStory);
    }

    @Override
    public Optional<SprintUserStory> getSprintUserStoryById(Integer id) {
        return sprintUserStoryRepository.findById(id);
    }

    @Override
    public List<SprintUserStory> getAllSprintUserStories() {
        return sprintUserStoryRepository.findAll();
    }

    @Override
    public SprintUserStory updateSprintUserStory(Integer id, SprintUserStory sprintUserStory) {

        SprintUserStory existing = sprintUserStoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("SprintUserStory introuvable avec id : " + id));


        if (sprintUserStory.getEstimationFinale() != null) {
            existing.setEstimationFinale(sprintUserStory.getEstimationFinale());
        }

        if (sprintUserStory.getCommitA() != null) {
            existing.setCommitA(sprintUserStory.getCommitA());
        }

        if (sprintUserStory.getStatut() != null) {
            existing.setStatut(sprintUserStory.getStatut());
        }

        if (sprintUserStory.getTermineA() != null) {
            existing.setTermineA(sprintUserStory.getTermineA());
        }

        if (sprintUserStory.getSprint() != null) {
            existing.setSprint(sprintUserStory.getSprint());
        }

        if (sprintUserStory.getUserStory() != null) {
            existing.setUserStory(sprintUserStory.getUserStory());
        }

        return sprintUserStoryRepository.save(existing);
    }

    @Override
    public void deleteSprintUserStory(Integer id) {

        SprintUserStory item = sprintUserStoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("SprintUserStory introuvable avec id : " + id));

        sprintUserStoryRepository.delete(item);
    }
}