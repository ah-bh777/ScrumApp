package com.ISICOD.ScrumApp.Services.Builders.Impl;

import com.ISICOD.ScrumApp.DTOs.Common.SessionOptionDTO;
import com.ISICOD.ScrumApp.Entities.SessionConfiguration;
import com.ISICOD.ScrumApp.Services.Builders.SessionOptionBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionOptionBuilderImpl implements SessionOptionBuilder {

    @Override
    public List<SessionOptionDTO> build(List<SessionConfiguration> configurations) {

        return configurations.stream()
                .map(configuration ->
                        SessionOptionDTO.builder()
                                .fonctionnaliteId(configuration.getFonctionnalite().getId())
                                .code(configuration.getFonctionnalite().getCode())
                                .nom(configuration.getFonctionnalite().getName())
                                .valeur(configuration.getValeur())
                                .build()
                )
                .toList();
    }
}