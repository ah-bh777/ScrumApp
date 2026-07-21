package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.StatutVote;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vote_poker")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotePoker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutVote statut;

    @Column(nullable = false)
    private Integer valeur;

    @Column(name = "cree_a")
    private LocalDateTime creeA;

    @PrePersist
    public void prePersist(){
        if(this.creeA == null){
            this.creeA = LocalDateTime.now();
        }
    }

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "participant_session_id", nullable = false)
    private ParticipantSession participantSession;


    @ManyToOne
    @JoinColumn(name = "tour_estimation_id", nullable = false)
    private TourEstimation tourEstimation;


}