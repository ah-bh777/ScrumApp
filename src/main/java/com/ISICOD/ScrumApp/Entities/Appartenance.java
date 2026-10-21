package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "appartenance",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_appartenance_utilisateur_espace",
                        columnNames = {"utilisateur_id", "espace_id"}
                )
        }
)

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "espace_id", nullable = false)
    private Espace espace;

}