package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.UserStory;
import com.ISICOD.ScrumApp.Repositories.UserStoryRepository;
import com.ISICOD.ScrumApp.Services.UserStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStoryServiceImpl implements UserStoryService {

    private final UserStoryRepository userStoryRepository;

    @Override
    public UserStory createUserStory(UserStory userStory) {
        return userStoryRepository.save(userStory);
    }

    @Override
    public Optional<UserStory> getUserStoryById(Integer id) {
        return userStoryRepository.findById(id);
    }

    @Override
    public List<UserStory> getAllUserStories() {
        return userStoryRepository.findAll();
    }

    @Override
    public UserStory updateUserStory(Integer id, UserStory userStory) {

        UserStory existing = userStoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("UserStory introuvable avec id : " + id));

        // Partial update

        if (userStory.getTitre() != null) {
            existing.setTitre(userStory.getTitre());
        }

        if (userStory.getDescription() != null) {
            existing.setDescription(userStory.getDescription());
        }

        if (userStory.getPriorite() != null) {
            existing.setPriorite(userStory.getPriorite());
        }

        if (userStory.getStoryPoints() != null) {
            existing.setStoryPoints(userStory.getStoryPoints());
        }

        if (userStory.getCreeA() != null) {
            existing.setCreeA(userStory.getCreeA());
        }

        if (userStory.getProductBacklog() != null) {
            existing.setProductBacklog(userStory.getProductBacklog());
        }

        return userStoryRepository.save(existing);
    }

    @Override
    public void deleteUserStory(Integer id) {

        UserStory userStory = userStoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("UserStory introuvable avec id : " + id));

        userStoryRepository.delete(userStory);
    }
}