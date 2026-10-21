
package com.ISICOD.ScrumApp.DTOs.DailyMeeting;

import lombok.Data;

import java.util.List;

@Data
public class DailySubmissionRequestDTO {

    private Integer utilisateurId;

    private Integer participantSessionId;

    private List<DailySubmissionContentDTO> contents;
}
