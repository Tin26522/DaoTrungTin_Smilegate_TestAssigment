package com.example.daotrungtin_smilegate_testassigment.dto;

import com.example.daotrungtin_smilegate_testassigment.entity.Category;
import com.example.daotrungtin_smilegate_testassigment.entity.Language;
import jakarta.validation.constraints.*;
import java.util.List;

public record GameDto(
        @NotBlank @Pattern(regexp = "^[A-Z0-9_\\-]+$") @Size(max = 64) String id,
        @NotNull Category category,
        @NotNull Language defaultLanguage,
        @NotNull @Size(min = 1) List<@NotNull GameNameDto> names
) {}