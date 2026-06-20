package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "code_session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "expire_a")
    private LocalDateTime expireA;

    @Column(name = "est_valide")
    private Boolean estValide;


    @ManyToOne
    @JoinColumn(name = "espace_id", nullable = false)
    private Espace espace;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
}