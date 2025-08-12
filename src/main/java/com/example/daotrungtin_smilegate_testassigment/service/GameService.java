package com.example.daotrungtin_smilegate_testassigment.service;

import com.example.daotrungtin_smilegate_testassigment.dto.GameDto;
import com.example.daotrungtin_smilegate_testassigment.dto.GameNameDto;
import com.example.daotrungtin_smilegate_testassigment.entity.*;
import com.example.daotrungtin_smilegate_testassigment.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class GameService {
    private final GameRepository repo;

    public GameService(GameRepository repo) { this.repo = repo; }

    public Page<GameDto> search(Category category, String q, Pageable pageable) {
        return repo.search(category, emptyToNull(q), pageable).map(this::toDto);
    }

    public GameDto get(String id) {
        return toDto(findById(id));
    }

    public GameDto create(GameDto dto) {
        if (repo.existsById(dto.id())) throw new IllegalArgumentException("ID already exists");
        validateNames(dto);
        Game g = toEntityNew(dto);
        return toDto(repo.save(g));
    }

    public GameDto update(String id, GameDto dto) {
        Game g = findById(id);
        validateNames(dto);
        g.setCategory(dto.category());
        g.setDefaultLanguage(dto.defaultLanguage());
        g.getNames().clear();
        for (GameNameDto n : dto.names()) {
            GameName en = GameName.builder()
                    .id(new GameNameId(id, n.language()))
                    .game(g)
                    .value(n.value())
                    .build();
            g.getNames().add(en);
        }
        return toDto(repo.save(g));
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public void deleteAll(Collection<String> ids) {
        repo.deleteAllById(ids);
    }

    private Game findById(String id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found: " + id));
    }

    private Game toEntityNew(GameDto dto) {
        Game g = Game.builder()
                .id(dto.id())
                .category(dto.category())
                .defaultLanguage(dto.defaultLanguage())
                .build();
        Set<GameName> set = new HashSet<>();
        for (GameNameDto n : dto.names()) {
            set.add(GameName.builder()
                    .id(new GameNameId(dto.id(), n.language()))
                    .game(g)
                    .value(n.value())
                    .build());
        }
        g.setNames(set);
        return g;
    }

    private GameDto toDto(Game g) {
        List<GameNameDto> names = g.getNames().stream()
                .map(n -> new GameNameDto(n.getId().getLanguage(), n.getValue()))
                .sorted(Comparator.comparing(n -> n.language().name()))
                .toList();
        return new GameDto(g.getId(), g.getCategory(), g.getDefaultLanguage(), names);
    }

    private void validateNames(GameDto dto) {
        Set<Language> langs = new HashSet<>();
        for (GameNameDto n : dto.names()) {
            if (!langs.add(n.language())) throw new IllegalArgumentException("Duplicate language: " + n.language());
        }
        if (!langs.contains(dto.defaultLanguage())) {
            throw new IllegalArgumentException("defaultLanguage must exist in names");
        }
    }

    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}