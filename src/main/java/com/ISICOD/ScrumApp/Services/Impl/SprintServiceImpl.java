package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.Sprint;
import com.ISICOD.ScrumApp.Repositories.SprintRepository;
import com.ISICOD.ScrumApp.Services.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;

    @Override
    public Sprint createSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    @Override
    public Optional<Sprint> getSprintById(Integer id) {
        return sprintRepository.findById(id);
    }

    @Override
    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }

    @Override
    public Sprint updateSprint(Integer id, Sprint sprint) {

        Sprint existing = sprintRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Sprint introuvable avec id : " + id));

        if (sprint.getTitre() != null) {
            existing.setTitre(sprint.getTitre());
        }

        if (sprint.getObjectif() != null) {
            existing.setObjectif(sprint.getObjectif());
        }

        if (sprint.getCommFinanceDeDate() != null) {
            existing.setCommFinanceDeDate(sprint.getCommFinanceDeDate());
        }

        if (sprint.getTermineA() != null) {
            existing.setTermineA(sprint.getTermineA());
        }

        if (sprint.getCapaciteMax() != null) {
            existing.setCapaciteMax(sprint.getCapaciteMax());
        }

        if (sprint.getCreeA() != null) {
            existing.setCreeA(sprint.getCreeA());
        }

        if (sprint.getEspace() != null) {
            existing.setEspace(sprint.getEspace());
        }

        return sprintRepository.save(existing);
    }

    @Override
    public void deleteSprint(Integer id) {

        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Sprint introuvable avec id : " + id));

        sprintRepository.delete(sprint);
    }
}