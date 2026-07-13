package com.ISICOD.ScrumApp.DTOs.ActionItem;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResumeDTO {

    private Integer id;
    private String description;
    private LocalDateTime envoyeA;
    private LocalDateTime luA;
}