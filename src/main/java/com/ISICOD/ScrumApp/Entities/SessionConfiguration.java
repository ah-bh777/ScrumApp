package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "session_configuration")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String valeur;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "fonctionalite_id" , nullable = false)
    private Fonctionnalite fonctionnalite ;
}