package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appartenance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appartenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rejoint_a")
    private LocalDateTime rejointA;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_attribue")
    private RoleEspace roleAttribue;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur ;

    @ManyToOne
    @JoinColumn(name = "espace_id")
    private Espace espace ;

}