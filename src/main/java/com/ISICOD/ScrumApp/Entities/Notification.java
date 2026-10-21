package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "envoye_a")
    private LocalDateTime envoyeA;

    @Column(name = "lu_a")
    private LocalDateTime luA;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "action_item_id")
    private ActionItem actionItem;

    @ManyToOne
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    @PrePersist
    public void prePersist() {
        if (envoyeA == null) {
            envoyeA = LocalDateTime.now();
        }
    }
}