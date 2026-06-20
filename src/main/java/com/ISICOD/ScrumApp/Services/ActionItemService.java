package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.ActionItem;

import java.util.List;
import java.util.Optional;

public interface ActionItemService {

    ActionItem createActionItem(ActionItem actionItem);

    Optional<ActionItem> getActionItemById(Integer id);

    List<ActionItem> getAllActionItems();

    ActionItem updateActionItem(Integer id, ActionItem actionItem);

    void deleteActionItem(Integer id);
}