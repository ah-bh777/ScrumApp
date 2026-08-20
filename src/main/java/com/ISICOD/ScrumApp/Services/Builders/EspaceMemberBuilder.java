package com.ISICOD.ScrumApp.Services.Builders;

import com.ISICOD.ScrumApp.DTOs.Espace.EspaceMemberDTO;
import com.ISICOD.ScrumApp.Entities.Appartenance;

public interface EspaceMemberBuilder {

    EspaceMemberDTO build(Appartenance appartenance);
}