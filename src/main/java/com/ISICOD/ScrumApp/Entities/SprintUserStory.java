package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.StatutSprintBacklogItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sprint_user_story")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintUserStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "estimation_finale")
    private LocalDateTime estimationFinale;

    @Column(name = "commit_a")
    private LocalDateTime commitA;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutSprintBacklogItem statut;

    @Column(name = "termine_a")
    private LocalDateTime termineA;

    @Column(nullable = false)
    private Boolean retenue;

    @ManyToOne
    @JoinColumn(name = "sprint_id", nullable = false)
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "user_story_id", nullable = false)
    private UserStory userStory;
}