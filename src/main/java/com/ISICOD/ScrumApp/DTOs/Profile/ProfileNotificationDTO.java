package com.ISICOD.ScrumApp.DTOs.Profile;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileNotificationDTO {

    private Integer notificationId;

    private String description;

    private Boolean lue;

    private LocalDateTime envoyeA;

    private LocalDateTime luA;

    private Integer actionItemId;
}