package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.DTOs.Sprint.SprintDetailsDTO;
import com.ISICOD.ScrumApp.Entities.Sprint;

import java.util.List;
import java.util.Optional;

public interface SprintService {

    Sprint createSprint(Sprint sprint);

    Optional<Sprint> getSprintById(Integer id);

    List<Sprint> getAllSprints();

    Sprint updateSprint(Integer id, Sprint sprint);

    void deleteSprint(Integer id);

    SprintDetailsDTO getSprintDetails(Integer sprintId);
}