package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(length = 20)
    private String telephone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "cree_a", nullable = false)
    private LocalDate creeA;

    @PrePersist
    public void prePersist() {
        if (creeA == null) {
            creeA = LocalDate.now();
        }
    }
    @OneToMany(mappedBy = "utilisateur")
    private List<Appartenance> appartenances;

    @OneToMany(mappedBy = "utilisateur")
    private List<Invitation> invitations ;

    @OneToMany(mappedBy = "createur")
    private List<ActionItem> actionItemsCrees;

    @OneToMany(mappedBy = "assigneA")
    private List<ActionItem> actionItemsAssignes;

    @OneToMany(mappedBy = "utilisateur")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "utilisateur")
    private List<ParticipantSession> participationsSession;

    @OneToMany(mappedBy = "utilisateur")
    private List<NoteRetro> noteRetros;

    @OneToMany(mappedBy = "utilisateur")
    private List<VoteDot> votesDot;

    @OneToMany(mappedBy = "utilisateur")
    private List<VotePoker> votesPoker;


}