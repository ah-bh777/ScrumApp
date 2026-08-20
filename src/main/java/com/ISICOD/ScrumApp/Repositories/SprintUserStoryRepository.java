package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.SprintUserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintUserStoryRepository
        extends JpaRepository<SprintUserStory, Integer> {

    List<SprintUserStory> findByUserStoryId(Integer userStoryId);

}