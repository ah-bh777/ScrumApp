package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyContentUpdateDTO {

    private Integer utilisateurId;

    private Integer sprintUserStoryId;

    // false/null: keep existing Story
    // true: use sprintUserStoryId, including null to remove Story
    private Boolean updateStory;

    private TypeDailyContent type;

    private String description;
}