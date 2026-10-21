
package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import lombok.Data;

@Data
public class DailySubmissionContentDTO {

    private Integer sprintUserStoryId;

    private TypeDailyContent type;

    private String description;
}
