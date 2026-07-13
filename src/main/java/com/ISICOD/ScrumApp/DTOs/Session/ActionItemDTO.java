package com.ISICOD.ScrumApp.DTOs.Session;

import com.ISICOD.ScrumApp.Enums.StatutActionItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionItemDTO {

    private Integer id;

    private String titre;

    private String description;

    private StatutActionItem status;

    private LocalDate echeance;

    private String assigneA;
}