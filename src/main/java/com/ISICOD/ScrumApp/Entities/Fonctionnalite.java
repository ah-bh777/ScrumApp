package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.FonctionnaliteCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "fonctionnalite")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fonctionnalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private FonctionnaliteCode code;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "fonctionnalite")
    private List<TypeSessionFonctionnalite> typeSessions;

    @OneToMany(mappedBy = "fonctionnalite")
    private List<SessionConfiguration> sessionConfigurationList ;
}