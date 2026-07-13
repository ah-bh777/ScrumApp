package com.ISICOD.ScrumApp.DTOs.Poker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourPokerDTO {

    private Integer tourId;

    private Integer numeroTour;

    private Boolean revele;

    private Integer valeurFinale;

    private List<VotePokerDTO> votes;

}