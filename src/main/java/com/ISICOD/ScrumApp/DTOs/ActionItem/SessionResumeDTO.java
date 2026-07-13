package com.ISICOD.ScrumApp.DTOs.ActionItem;

import com.ISICOD.ScrumApp.Enums.StatutSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionResumeDTO {

    private Integer id;
    private LocalDateTime commenceA;
    private LocalDateTime termineA;
    private StatutSession status;
}