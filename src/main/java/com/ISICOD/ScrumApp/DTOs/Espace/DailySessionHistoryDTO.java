package com.ISICOD.ScrumApp.DTOs.Espace;

import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailySessionHistoryDTO {

    private Integer sessionId;

    private LocalDateTime commenceA;

    private LocalDateTime termineA;

    private TypeSessionCode type;
}