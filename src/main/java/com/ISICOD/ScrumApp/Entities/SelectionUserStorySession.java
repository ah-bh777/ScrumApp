package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "selection_user_story_session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectionUserStorySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "selectionne_a")
    private LocalDateTime selectionneA;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "user_story_id", nullable = false)
    private UserStory userStory;

    @OneToMany(mappedBy = "selectionUserStorySession")
    private List<TourEstimation> tourEstimations;

}