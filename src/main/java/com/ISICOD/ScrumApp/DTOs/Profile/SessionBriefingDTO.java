package com.ISICOD.ScrumApp.DTOs.Profile;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionBriefingDTO {

    private Integer participants;

    // Poker
    private Integer storiesEstimated;
    private Integer rounds;
    private Integer totalStoryPoints;

    // Retro
    private Integer groups;
    private Integer notes;
    private Integer votes;
    private Integer actionItemsCreated;

    // Daily
    private Integer updates;
    private Integer blockers;
}