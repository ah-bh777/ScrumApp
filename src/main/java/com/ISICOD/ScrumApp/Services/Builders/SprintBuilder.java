package com.ISICOD.ScrumApp.Services.Builders;

import com.ISICOD.ScrumApp.DTOs.Sprint.SprintDetailsDTO;
import com.ISICOD.ScrumApp.Entities.Sprint;

public interface SprintBuilder {

    SprintDetailsDTO build(Sprint sprint);

}