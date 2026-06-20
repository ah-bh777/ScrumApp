package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.DailyContent;

import java.util.List;
import java.util.Optional;

public interface DailyContentService {

    DailyContent createDailyContent(DailyContent dailyContent);

    Optional<DailyContent> getDailyContentById(Integer id);

    List<DailyContent> getDailyContentAll();

    DailyContent updateDailyContent(Integer id, DailyContent dailyContent);

    void deleteDailyContent(Integer id);

}