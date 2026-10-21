package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "daily_story_reservation",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_daily_story_reservation_session_story",
                        columnNames = {"session_id", "sprint_user_story_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyStoryReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sprint_user_story_id", nullable = false)
    private SprintUserStory sprintUserStory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_session_id", nullable = false)
    private ParticipantSession participantSession;

    @Column(name = "reserved_at", nullable = false)
    private LocalDateTime reservedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @PrePersist
    public void prePersist() {
        if (reservedAt == null) {
            reservedAt = LocalDateTime.now();
        }

        if (expiresAt == null) {
            expiresAt = reservedAt.plusMinutes(10);
        }
    }
}
