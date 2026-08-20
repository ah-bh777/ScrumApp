package com.ISICOD.ScrumApp.Services.Builders;

import com.ISICOD.ScrumApp.DTOs.Espace.EspaceMemberListDTO;
import com.ISICOD.ScrumApp.Entities.Appartenance;

public interface EspaceMemberListBuilder {

    EspaceMemberListDTO build(
            Appartenance appartenance
    );
}