package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.ActionItem;
import com.ISICOD.ScrumApp.Repositories.ActionItemRepository;
import com.ISICOD.ScrumApp.Services.ActionItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActionItemServiceImpl implements ActionItemService {

    private final ActionItemRepository actionItemRepository;

    @Override
    public ActionItem createActionItem(ActionItem actionItem) {
        return actionItemRepository.save(actionItem);
    }

    @Override
    public Optional<ActionItem> getActionItemById(Integer id) {
        return actionItemRepository.findById(id);
    }

    @Override
    public List<ActionItem> getAllActionItems() {
        return actionItemRepository.findAll();
    }

    @Override
    public ActionItem updateActionItem(Integer id, ActionItem actionItem) {

        ActionItem existing = actionItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ActionItem introuvable avec id : " + id));


        if (actionItem.getTitre() != null) {
            existing.setTitre(actionItem.getTitre());
        }

        if (actionItem.getDescription() != null) {
            existing.setDescription(actionItem.getDescription());
        }

        if (actionItem.getEcheance() != null) {
            existing.setEcheance(actionItem.getEcheance());
        }

        if (actionItem.getStatus() != null) {
            existing.setStatus(actionItem.getStatus());
        }

        if (actionItem.getCreeA() != null) {
            existing.setCreeA(actionItem.getCreeA());
        }

        if (actionItem.getEspace() != null) {
            existing.setEspace(actionItem.getEspace());
        }

        if (actionItem.getSession() != null) {
            existing.setSession(actionItem.getSession());
        }

        if (actionItem.getCreateur() != null) {
            existing.setCreateur(actionItem.getCreateur());
        }

        if (actionItem.getAssigneA() != null) {
            existing.setAssigneA(actionItem.getAssigneA());
        }

        return actionItemRepository.save(existing);
    }

    @Override
    public void deleteActionItem(Integer id) {

        ActionItem actionItem = actionItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ActionItem introuvable avec id : " + id));

        actionItemRepository.delete(actionItem);
    }
}