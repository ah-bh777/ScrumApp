package com.ISICOD.ScrumApp.DTOs.ActionItem;

public record ActionItemNavigationDTO(
        Integer actionItemId,
        Integer sprintId,
        Integer espaceId,
        Integer sessionId
) {
}