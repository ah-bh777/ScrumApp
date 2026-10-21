package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyContentRequestDTO {

    @NotNull
    private Integer participantSessionId;

    @NotNull
    private Integer sprintUserStoryId;

    @NotNull
    private TypeDailyContent type;

    @NotBlank
    private String description;

    private Integer utilisateurId;
}