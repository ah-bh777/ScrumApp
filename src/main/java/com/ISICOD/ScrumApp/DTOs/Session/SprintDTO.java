package com.ISICOD.ScrumApp.DTOs.Session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintDTO {

    private Integer id;

    private String titre;

    private String objectif;

    private LocalDateTime commenceDe;

    private LocalDateTime termineA;
}