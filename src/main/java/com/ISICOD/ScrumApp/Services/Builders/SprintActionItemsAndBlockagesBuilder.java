package com.ISICOD.ScrumApp.Services.Builders;

import com.ISICOD.ScrumApp.DTOs.ActionItemsAndDailyContent.SprintActionItemsAndBlockagesDTO;
import com.ISICOD.ScrumApp.Entities.ActionItem;
import com.ISICOD.ScrumApp.Entities.DailyContent;
import com.ISICOD.ScrumApp.Entities.Sprint;

import java.util.List;

public interface SprintActionItemsAndBlockagesBuilder {

    SprintActionItemsAndBlockagesDTO build(
            Sprint sprint,
            List<ActionItem> actionItems,
            List<DailyContent> blockages
    );
}