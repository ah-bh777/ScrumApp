package com.ISICOD.ScrumApp.Services.Builders;

import com.ISICOD.ScrumApp.DTOs.Session.SessionDetailsDTO;
import com.ISICOD.ScrumApp.Entities.Session;

public interface SessionDetailsBuilder {

    SessionDetailsDTO build(Session session);

}