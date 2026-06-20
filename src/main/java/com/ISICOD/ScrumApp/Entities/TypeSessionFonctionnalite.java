package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type_session_fonctionnalite")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeSessionFonctionnalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Boolean inclus;

    @Column(nullable = false)
    private Boolean obligatoire;

    @ManyToOne
    @JoinColumn(name = "type_session_id", nullable = false)
    private TypeSession typeSession;

    @ManyToOne
    @JoinColumn(name = "fonctionnalite_id", nullable = false)
    private Fonctionnalite fonctionnalite;
}