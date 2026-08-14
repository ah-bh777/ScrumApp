package com.ISICOD.ScrumApp.DTOs.ActionItemsAndDailyContent;

import com.ISICOD.ScrumApp.DTOs.ActionItem.ActionItemDetailsDTO;
import com.ISICOD.ScrumApp.DTOs.Daily.DailyContentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintActionItemsAndBlockagesDTO {

    private Integer sprintId;

    private List<ActionItemDetailsDTO> actionItems;

    private List<DailyContentDTO> blockages;
}

