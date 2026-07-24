package com.ISICOD.ScrumApp.Services.Builders;

import com.ISICOD.ScrumApp.DTOs.Common.SessionOptionDTO;
import com.ISICOD.ScrumApp.Entities.SessionConfiguration;

import java.util.List;

public interface SessionOptionBuilder {

    List<SessionOptionDTO> build(List<SessionConfiguration> configurations);

}