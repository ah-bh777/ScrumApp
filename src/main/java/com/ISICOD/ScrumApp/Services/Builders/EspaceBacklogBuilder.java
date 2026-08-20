package com.ISICOD.ScrumApp.Services.Builders;

import com.ISICOD.ScrumApp.DTOs.Espace.EspaceBacklogDTO;
import com.ISICOD.ScrumApp.Entities.Espace;
import com.ISICOD.ScrumApp.Entities.ProductBacklog;

public interface EspaceBacklogBuilder {

    EspaceBacklogDTO build(
            Espace espace,
            ProductBacklog productBacklog
    );
}