package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.TypeColonne;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "note_retro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteRetro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING) // see this oneee from the epics
    @Column(name = "type_colonne", nullable = false)
    private TypeColonne typeColonne;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @Column(name = "cree_a")
    private LocalDateTime creeA;

    @PrePersist
    public void prePresist(){
        if (this.creeA == null){
            this.creeA = LocalDateTime.now();
        }
    }

    @ManyToOne
    @JoinColumn(name = "groupe_note_id", nullable = false)
    private GroupeNote groupeNote;

    @ManyToOne
    @JoinColumn(name = "Utilisateur_id" , nullable = false)
    private Utilisateur utilisateur ;

    @OneToMany(mappedBy = "noteRetro")
    private List<VoteDot> votesDot;

}