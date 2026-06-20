package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.PhaseRetro;
import com.ISICOD.ScrumApp.Enums.TypeTemplateRetro;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "etat_retro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtatRetro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase_actuelle", nullable = false)
    private PhaseRetro phaseActuelle;

    @Column(name = "commence_a")
    private LocalDateTime commenceA = LocalDateTime.now();

    @Column(name = "termine_a")
    private LocalDateTime termineA ;

    @PrePersist
    public void prePersist(){
        if (this.commenceA == null){
            this.commenceA = LocalDateTime.now();
        }
    }

    @Column(name = "type_template") // this one suppose to be an enum
    @Enumerated(EnumType.STRING)
    private TypeTemplateRetro typeTemplate;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
}