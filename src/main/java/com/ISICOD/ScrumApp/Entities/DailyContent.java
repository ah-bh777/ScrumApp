package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.TypeDailyContent;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_content")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenu;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_contenu", nullable = false)
    private TypeDailyContent typeContenu;

    @Column(name = "cree_a")
    private LocalDateTime creeA;

    @PrePersist
    public void prePersist() {
        if (creeA == null) {
            creeA = LocalDateTime.now();
        }
    }

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "participant_session_id", nullable = false)
    private ParticipantSession participantSession;

    @ManyToOne
    @JoinColumn(name = "sprint_user_story_id" , nullable = false)
    private SprintUserStory sprintUserStory;
}