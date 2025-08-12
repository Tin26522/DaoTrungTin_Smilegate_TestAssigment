package com.example.daotrungtin_smilegate_testassigment.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class GameNameId implements Serializable {
    private String gameId;
    private Language language;
}