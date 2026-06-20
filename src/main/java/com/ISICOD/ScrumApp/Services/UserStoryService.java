package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.UserStory;

import java.util.List;
import java.util.Optional;

public interface UserStoryService {

    UserStory createUserStory(UserStory userStory);

    Optional<UserStory> getUserStoryById(Integer id);

    List<UserStory> getAllUserStories();

    UserStory updateUserStory(Integer id, UserStory userStory);

    void deleteUserStory(Integer id);
}