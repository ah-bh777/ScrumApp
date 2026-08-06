package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.EtatExecutionSprint;
import com.ISICOD.ScrumApp.Enums.StatutSprintBacklogItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private Integer estimationFinale;

    @Column(name = "commit_a")
    private LocalDateTime commitA;

    @PrePersist
    public void prePresist(){
        this.commitA = LocalDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutSprintBacklogItem statut;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_execution", nullable = false)
    @Builder.Default
    private EtatExecutionSprint etatExecution = EtatExecutionSprint.A_FAIRE;

    @Column(name = "termine_a")
    private LocalDateTime termineA;


    @ManyToOne
    @JoinColumn(name = "assigne_a")
    private Utilisateur assigneA;

    @ManyToOne
    @JoinColumn(name = "sprint_id", nullable = false)
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "user_story_id", nullable = false)
    private UserStory userStory;

    @OneToMany( mappedBy = "sprintUserStory")
    private List<DailyContent> dailyContents;

    @OneToMany(mappedBy = "sprintUserStory")
    private List<SelectionUserStorySession> selectionUserStorySessions;
}