package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.DTOs.ActionItemsAndDailyContent.SprintActionItemsAndBlockagesDTO;
import com.ISICOD.ScrumApp.DTOs.Sprint.SprintDetailsDTO;
import com.ISICOD.ScrumApp.Entities.ActionItem;
import com.ISICOD.ScrumApp.Entities.DailyContent;
import com.ISICOD.ScrumApp.Entities.Sprint;
import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import com.ISICOD.ScrumApp.Repositories.ActionItemRepository;
import com.ISICOD.ScrumApp.Repositories.DailyContentRepository;
import com.ISICOD.ScrumApp.Repositories.SprintRepository;
import com.ISICOD.ScrumApp.Services.Builders.SprintActionItemsAndBlockagesBuilder;
import com.ISICOD.ScrumApp.Services.Builders.SprintBuilder;
import com.ISICOD.ScrumApp.Services.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;

    private final SprintBuilder sprintBuilder;

    private final ActionItemRepository actionItemRepository;

    private final DailyContentRepository dailyContentRepository;

    private final SprintActionItemsAndBlockagesBuilder
            sprintActionItemsAndBlockagesBuilder;


    // ============================================================
    // CREATE
    // ============================================================

    @Override
    public Sprint createSprint(Sprint sprint) {

        return sprintRepository.save(sprint);
    }


    // ============================================================
    // GET BY ID
    // ============================================================

    @Override
    public Optional<Sprint> getSprintById(Integer id) {

        return sprintRepository.findById(id);
    }


    // ============================================================
    // GET ALL
    // ============================================================

    @Override
    public List<Sprint> getAllSprints() {

        return sprintRepository.findAll();
    }


    // ============================================================
    // UPDATE
    // ============================================================

    @Override
    public Sprint updateSprint(
            Integer id,
            Sprint sprint
    ) {

        Sprint existing = sprintRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sprint introuvable avec id : " + id
                        )
                );

        if (sprint.getTitre() != null) {
            existing.setTitre(
                    sprint.getTitre()
            );
        }

        if (sprint.getObjectif() != null) {
            existing.setObjectif(
                    sprint.getObjectif()
            );
        }

        if (sprint.getCommFinanceDeDate() != null) {
            existing.setCommFinanceDeDate(
                    sprint.getCommFinanceDeDate()
            );
        }

        if (sprint.getTermineA() != null) {
            existing.setTermineA(
                    sprint.getTermineA()
            );
        }

        if (sprint.getCapaciteMax() != null) {
            existing.setCapaciteMax(
                    sprint.getCapaciteMax()
            );
        }

        if (sprint.getCreeA() != null) {
            existing.setCreeA(
                    sprint.getCreeA()
            );
        }

        if (sprint.getEspace() != null) {
            existing.setEspace(
                    sprint.getEspace()
            );
        }

        return sprintRepository.save(existing);
    }


    // ============================================================
    // DELETE
    // ============================================================

    @Override
    public void deleteSprint(Integer id) {

        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sprint introuvable avec id : " + id
                        )
                );

        sprintRepository.delete(sprint);
    }


    // ============================================================
    // SPRINT DETAILS
    // ============================================================

    @Override
    public SprintDetailsDTO getSprintDetails(
            Integer sprintId
    ) {

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sprint introuvable avec id : " + sprintId
                        )
                );

        return sprintBuilder.build(sprint);
    }


    // ============================================================
    // SPRINT ACTION ITEMS + DAILY BLOCKAGES
    // ============================================================

    @Override
    public SprintActionItemsAndBlockagesDTO
    getSprintActionItemsAndBlockages(
            Integer sprintId
    ) {

        // --------------------------------------------------------
        // 1. Validate sprint
        // --------------------------------------------------------

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sprint introuvable avec id : "
                                        + sprintId
                        )
                );


        // --------------------------------------------------------
        // 2. Get Action Items belonging to this sprint
        //
        // ActionItem -> Session -> Sprint
        // --------------------------------------------------------

        List<ActionItem> actionItems =
                actionItemRepository.findBySessionSprintId(
                        sprintId
                );


        // --------------------------------------------------------
        // 3. Get Daily BLOCKAGE content belonging to this sprint
        //
        // DailyContent -> SprintUserStory -> Sprint
        //
        // Filtering BLOCKAGE happens in the database query.
        // --------------------------------------------------------

        List<DailyContent> blockages =
                dailyContentRepository
                        .findBySprintUserStorySprintIdAndTypeContenu(
                                sprintId,
                                TypeDailyContent.BLOCKAGE
                        );


        // --------------------------------------------------------
        // 4. Build the final DTO
        // --------------------------------------------------------

        return sprintActionItemsAndBlockagesBuilder.build(
                sprint,
                actionItems,
                blockages
        );
    }
}