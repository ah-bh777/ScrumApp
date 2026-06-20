package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.StatutSession;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "commence_a")
    private LocalDateTime commenceA;

    @Column(name = "termine_a")
    private LocalDateTime termineA;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutSession status;

    @Column(name = "crea_a", nullable = false)
    private LocalDateTime creaA;

    @ManyToOne
    @JoinColumn(name = "espace_id" ,nullable = false)
    private Espace espace;

    @ManyToOne
    @JoinColumn(name = "sprint_id", nullable = false)
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "type_session_id", nullable = false)
    private TypeSession typeSession ;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<CodeSession> codeSessions;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<SessionConfiguration> configurations;

    @OneToMany(mappedBy = "session")
    private List<ActionItem> actionItems;

    @OneToMany(mappedBy = "session")
    private List<SelectionUserStorySession> selections;

    @OneToMany(mappedBy = "session")
    private List<ParticipantSession> participants;

    @OneToMany(mappedBy = "session")
    private List<DailyContent> contenusDaily;

    @OneToMany(mappedBy = "session")
    private List<EtatRetro> etatsRetro;

    @OneToMany(mappedBy = "session")
    private List<GroupeNote> groupesNote;

    @OneToMany(mappedBy = "session")
    private List<VoteDot> votesDot;

}