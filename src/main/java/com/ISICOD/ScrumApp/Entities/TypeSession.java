package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "type_session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TypeSessionCode code;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "typeSession")
    private List<TypeSessionFonctionnalite> fonctionnalites;

    @OneToMany(mappedBy = "typeSession")
    private List<Session> sessions;

}