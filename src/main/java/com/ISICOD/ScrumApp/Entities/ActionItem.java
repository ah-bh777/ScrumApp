package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.StatutActionItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "action_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate echeance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutActionItem status;

    @Column(nullable = false , name = "cree_a")
    private LocalDateTime creeA;

    @PrePersist
    public void prePersist() {
        if (creeA == null) {
            creeA = LocalDateTime.now();
        }
    }

    @ManyToOne
    @JoinColumn(name = "espace_id", nullable = false)
    private Espace espace;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "createur_id", nullable = false)
    private Utilisateur createur;

    @ManyToOne
    @JoinColumn(name = "assigne_a_id" , nullable = false)
    private Utilisateur assigneA;

    @OneToMany(mappedBy = "actionItem")
    private List<Notification> notifications;
}