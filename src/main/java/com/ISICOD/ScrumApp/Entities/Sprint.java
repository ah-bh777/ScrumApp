package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sprint")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String objectif;

    @Column(name = "commence_de")
    private LocalDateTime commFinanceDeDate;

    @Column(name = "termine_a")
    private LocalDateTime termineA;

    @Column(name = "capacite_max")
    private Integer capaciteMax;

    @Column(name = "cree_a")
    private LocalDateTime creeA;

    @ManyToOne
    @JoinColumn(name = "espace_id" , nullable = false)
    private Espace espace ;

    @OneToMany(mappedBy = "sprint")
    private List<SprintUserStory> sprintUserStories;

    @OneToMany(mappedBy = "sprint")
    private List<Session> sessions;
}