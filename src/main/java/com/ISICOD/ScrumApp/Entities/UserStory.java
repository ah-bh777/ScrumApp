package com.ISICOD.ScrumApp.Entities;

import com.ISICOD.ScrumApp.Enums.PrioriteUserStory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_story")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioriteUserStory priorite;

    @Column(name = "story_points")
    private Integer storyPoints;

    @Column(name = "cree_a", nullable = false)
    private LocalDateTime creeA;

    @ManyToOne
    @JoinColumn(name = "product_backlog_id" , nullable = false)
    private ProductBacklog productBacklog ;

    @OneToMany(mappedBy = "userStory")
    private List<SprintUserStory> sprintUserStories;


}