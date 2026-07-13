package com.ISICOD.ScrumApp.Services.Builders;

import com.ISICOD.ScrumApp.DTOs.Retro.RetroSessionDTO;
import com.ISICOD.ScrumApp.Entities.Session;

public interface RetroBuilder {

    RetroSessionDTO build(Session session);

}