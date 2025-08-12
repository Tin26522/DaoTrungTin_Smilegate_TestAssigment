package com.example.daotrungtin_smilegate_testassigment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Game {
    @Id
    @Column(length = 64, nullable = false, updatable = false)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "default_language", nullable = false, length = 8)
    private Language defaultLanguage;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<GameName> names = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }
}