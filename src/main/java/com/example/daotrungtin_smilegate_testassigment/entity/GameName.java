package com.example.daotrungtin_smilegate_testassigment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "game_names")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GameName {
    @EmbeddedId
    private GameNameId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("gameId")
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false, length = 100)
    private String value;
}