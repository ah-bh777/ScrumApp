package com.ISICOD.ScrumApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product_backlog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBacklog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cree_a", nullable = false)
    private LocalDateTime creeA;

    @OneToOne
    @JoinColumn(name = "espace_id", unique = true, nullable = false)
    private Espace espace;

    @OneToMany(mappedBy = "productBacklog")
    private List<UserStory> userStories;
}