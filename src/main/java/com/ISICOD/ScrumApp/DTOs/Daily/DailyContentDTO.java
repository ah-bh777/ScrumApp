package com.ISICOD.ScrumApp.DTOs.Daily;

import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyContentDTO {

    private Integer dailyId;

    private String contenu;

    private TypeDailyContent typeContenu;

    private LocalDateTime creeA;

    private ParticipantDailyDTO participant;

    private UtilisateurDailyDTO utilisateur;

    private UserStoryDailyDTO userStory;

}