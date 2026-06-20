package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.RoleEspace;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "participant_session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String pseudo;

    @Column(name = "est_invite")
    private Boolean estInvite;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_session", nullable = false)
    private RoleEspace roleSession;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = true)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @OneToMany(mappedBy = "participantSession")
    private List<DailyContent> contenusDaily;

    @OneToMany(mappedBy = "participantSession")
    private List<VoteDot> votesDot;

    @OneToMany(mappedBy = "participantSession")
    private List<VotePoker> votesPoker;
}