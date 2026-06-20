package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote_dot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteDot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cree_a")
    private LocalDateTime creeA;

    @PrePersist
    public void prePersist() {
        if (creeA == null) {
            creeA = LocalDateTime.now();
        }
    }

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "participant_session_id", nullable = false)
    private ParticipantSession participantSession;

    @ManyToOne
    @JoinColumn(name = "note_retro_id", nullable = false)
    private NoteRetro noteRetro;
}