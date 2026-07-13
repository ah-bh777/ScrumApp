package com.ISICOD.ScrumApp.Services.Builders;

import com.ISICOD.ScrumApp.DTOs.Daily.DailySessionDTO;
import com.ISICOD.ScrumApp.Entities.Session;

public interface DailyBuilder {

    DailySessionDTO build(Session session);

}