package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.SprintUserStory;

import java.util.List;
import java.util.Optional;

public interface SprintUserStoryService {

    SprintUserStory createSprintUserStory(SprintUserStory sprintUserStory);

    Optional<SprintUserStory> getSprintUserStoryById(Integer id);

    List<SprintUserStory> getAllSprintUserStories();

    SprintUserStory updateSprintUserStory(Integer id, SprintUserStory sprintUserStory);

    void deleteSprintUserStory(Integer id);

}