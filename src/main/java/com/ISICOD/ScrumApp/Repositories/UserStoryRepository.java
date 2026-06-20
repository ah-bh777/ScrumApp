package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Integer> {
}