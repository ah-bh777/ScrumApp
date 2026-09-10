package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyContentDTO {

    private Integer dailyContentId;

    private Integer participantId;

    private Integer sprintUserStoryId;

    private Integer userStoryId;

    private String userStoryTitre;

    private TypeDailyContent type;

    private String description;

    private LocalDateTime createdAt;
}