package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "espace")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Espace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nom;

    @Column(name = "nom_equipe")
    private String nomEquipe;

    @Column(name = "est_active")
    private Boolean estActive;

    @Column(name = "capacite" , nullable = false)
    private Integer capacite;

    @Column(name = "cree_a", nullable = false)
    private LocalDate creeA = LocalDate.now();

    @PrePersist
    public void perPresist(){
        if (creeA == null){
            creeA = LocalDate.now();
        }
    }

    @OneToMany(mappedBy = "espace")
    private List<Appartenance> appartenances;

    @OneToMany(mappedBy = "espace")
    private List<Invitation> invitations ;

    @OneToOne(mappedBy = "espace")
    private ProductBacklog productBacklog;

    @OneToMany(mappedBy = "espace")
    private List<Sprint> sprints;

    @OneToMany(mappedBy = "espace")
    private List<Session> sessions;

    @OneToMany(mappedBy = "espace", cascade = CascadeType.ALL)
    private List<CodeSession> codeSessions;

    @OneToMany(mappedBy = "espace")
    private List<ActionItem> actionItems;
}