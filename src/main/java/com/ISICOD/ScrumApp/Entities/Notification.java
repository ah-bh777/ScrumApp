package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
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

    @PrePersist
    public void prePersist() {
        if (envoyeA == null) {
            envoyeA = LocalDateTime.now();
        }
    }

    @Column(name = "lu_a")
    private LocalDateTime luA;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "action_item_id", nullable = false)
    private ActionItem actionItem;
}