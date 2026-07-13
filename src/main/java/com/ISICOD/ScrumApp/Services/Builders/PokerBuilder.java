package com.ISICOD.ScrumApp.Services.Builders;

import com.ISICOD.ScrumApp.DTOs.Poker.PokerSessionDTO;
import com.ISICOD.ScrumApp.Entities.Session;

public interface PokerBuilder {

    PokerSessionDTO build(Session session);

}