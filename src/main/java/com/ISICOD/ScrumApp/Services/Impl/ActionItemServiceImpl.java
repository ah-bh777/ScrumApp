package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.DTOs.ActionItem.*;
import com.ISICOD.ScrumApp.Entities.ActionItem;
import com.ISICOD.ScrumApp.Entities.Notification;
import com.ISICOD.ScrumApp.Entities.Session;
import com.ISICOD.ScrumApp.Repositories.ActionItemRepository;
import com.ISICOD.ScrumApp.Repositories.NotificationRepository;
import com.ISICOD.ScrumApp.Services.ActionItemService;
import com.ISICOD.ScrumApp.Services.Impl.notificationPublisher.NotificationEventPublisher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import com.ISICOD.ScrumApp.Entities.Notification;
import com.ISICOD.ScrumApp.Repositories.NotificationRepository;
import com.ISICOD.ScrumApp.Services.Impl.notificationPublisher.NotificationEventPublisher;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ActionItemServiceImpl implements ActionItemService {

    private final ActionItemRepository actionItemRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationEventPublisher notificationEventPublisher;


    @Override
    @Transactional
    public ActionItem createActionItem(ActionItem actionItem) {
        ActionItem saved = actionItemRepository.save(actionItem);

        notifyAssignee(saved);

        return saved;
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
    @Transactional
    public ActionItem updateActionItem(Integer id, ActionItem actionItem) {

        ActionItem existing = actionItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ActionItem introuvable avec id : " + id));

        Integer previousAssigneeId = existing.getAssigneA() != null
                ? existing.getAssigneA().getId()
                : null;

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

        ActionItem saved = actionItemRepository.save(existing);

        Integer newAssigneeId = saved.getAssigneA() != null
                ? saved.getAssigneA().getId()
                : null;

        if (newAssigneeId != null
                && !Objects.equals(previousAssigneeId, newAssigneeId)) {
            notifyAssignee(saved);
        }

        return saved;
    }

    @Override
    public void deleteActionItem(Integer id) {

        ActionItem actionItem = actionItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ActionItem introuvable avec id : " + id));

        actionItemRepository.delete(actionItem);
    }


    @Override
    public ActionItemDetailsDTO getActionItemDetails(Integer id) {

        ActionItem actionItem = actionItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ActionItem introuvable avec id : " + id));

        return ActionItemDetailsDTO.builder()

                // Action Item
                .id(actionItem.getId())
                .titre(actionItem.getTitre())
                .description(actionItem.getDescription())
                .echeance(actionItem.getEcheance())
                .status(actionItem.getStatus())
                .creeA(actionItem.getCreeA())

                // Creator
                .createur(
                        UtilisateurResumeDTO.builder()
                                .id(actionItem.getCreateur().getId())
                                .nom(actionItem.getCreateur().getNom())
                                .prenom(actionItem.getCreateur().getPrenom())
                                .email(actionItem.getCreateur().getEmail())
                                .build()
                )

                // Assigned user (can be null)
                .assigneA(
                        actionItem.getAssigneA() == null
                                ? null
                                : UtilisateurResumeDTO.builder()
                                .id(actionItem.getAssigneA().getId())
                                .nom(actionItem.getAssigneA().getNom())
                                .prenom(actionItem.getAssigneA().getPrenom())
                                .email(actionItem.getAssigneA().getEmail())
                                .build()
                )

                // Session
                .session(
                        SessionResumeDTO.builder()
                                .id(actionItem.getSession().getId())
                                .commenceA(actionItem.getSession().getCommenceA())
                                .termineA(actionItem.getSession().getTermineA())
                                .status(actionItem.getSession().getStatus())
                                .build()
                )

                // Notifications
                .notifications(
                        actionItem.getNotifications()
                                .stream()
                                .map(notification -> NotificationResumeDTO.builder()
                                        .id(notification.getId())
                                        .description(notification.getDescription())
                                        .envoyeA(notification.getEnvoyeA())
                                        .luA(notification.getLuA())
                                        .build())
                                .toList()
                )

                .build();
    }

    private void notifyAssignee(ActionItem actionItem) {

        if (actionItem.getAssigneA() == null) {
            return;
        }

        Notification notification = Notification.builder()
                .utilisateur(actionItem.getAssigneA())
                .actionItem(actionItem)
                .description(
                        "Un Action Item vous a été assigné : "
                                + actionItem.getTitre()
                )
                .build();

        Notification savedNotification =
                notificationRepository.save(notification);

        notificationEventPublisher.publish(savedNotification);
    }


    @Override
    @Transactional
    public ActionItemNavigationDTO getNavigationContext(Integer actionItemId) {

        ActionItem actionItem = actionItemRepository.findById(actionItemId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Action Item introuvable avec id : " + actionItemId
                ));

        Session session = actionItem.getSession();

        return new ActionItemNavigationDTO(
                actionItem.getId(),
                session.getSprint().getId(),
                session.getEspace().getId(),
                session.getId()
        );
    }
}