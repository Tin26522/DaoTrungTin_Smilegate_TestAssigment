package com.example.daotrungtin_smilegate_testassigment.controller;

import com.example.daotrungtin_smilegate_testassigment.dto.GameDto;
import com.example.daotrungtin_smilegate_testassigment.entity.Category;
import com.example.daotrungtin_smilegate_testassigment.service.GameService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService service;

    public GameController(GameService service) { this.service = service; }

    @GetMapping
    public Page<GameDto> list(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.search(category, q, PageRequest.of(page, size, Sort.by("id").ascending()));
    }

    @GetMapping("/{id}")
    public GameDto get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<GameDto> create(@RequestBody @Valid GameDto dto) {
        GameDto created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/games/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public GameDto update(@PathVariable String id, @RequestBody @Valid GameDto dto) {
        if (dto.id() != null && !id.equals(dto.id())) {
            throw new IllegalArgumentException("Path id and body id must match");
        }
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> bulkDelete(@RequestParam List<String> ids) {
        service.deleteAll(ids);
        return ResponseEntity.noContent().build();
    }
}