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

    private TypeDailyContent type;

    private String description;
}