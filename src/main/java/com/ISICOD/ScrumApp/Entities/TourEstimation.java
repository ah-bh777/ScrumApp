package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tour_estimation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourEstimation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero_tour")
    private Integer numeroTour;

    private Boolean revele;

    @Column(name = "valeur_finale")
    private Integer valeurFinale;

    @Column(name = "cree_a")
    private LocalDateTime creeA;

    @ManyToOne
    @JoinColumn(name = "selection_user_story_session_id", nullable = false)
    private SelectionUserStorySession selectionUserStorySession;

    @OneToMany(mappedBy = "tourEstimation")
    private List<VotePoker> votePokers;

}