package com.ISICOD.ScrumApp.DTOs.Poker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectionPokerDTO {

    private Integer selectionId;

    private LocalDateTime selectionneA;

    private SprintUserStoryPokerDTO sprintUserStory;

    private List<TourPokerDTO> tours;

}