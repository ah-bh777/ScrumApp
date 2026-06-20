package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invitation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "expire_a", nullable = false)
    private LocalDateTime expireA;

    @Column(name = "est_valide")
    private Boolean estValide;

    @Enumerated(EnumType.STRING)
    private RoleEspace role;

    @ManyToOne
    @JoinColumn(name = "espace_id")
    private Espace espace ;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur ;

}