package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "groupe_note")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupeNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String label;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @OneToMany(mappedBy = "groupeNote")
    private List<NoteRetro> notesRetro;
}