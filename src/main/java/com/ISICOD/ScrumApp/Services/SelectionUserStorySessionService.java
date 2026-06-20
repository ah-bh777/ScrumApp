package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.SelectionUserStorySession;

import java.util.List;
import java.util.Optional;

public interface SelectionUserStorySessionService {

    SelectionUserStorySession createSelection(
            SelectionUserStorySession selection);

    Optional<SelectionUserStorySession> getSelectionById(Integer id);

    List<SelectionUserStorySession> getAllSelections();

    SelectionUserStorySession updateSelection(
            Integer id,
            SelectionUserStorySession selection);

    void deleteSelection(Integer id);
}