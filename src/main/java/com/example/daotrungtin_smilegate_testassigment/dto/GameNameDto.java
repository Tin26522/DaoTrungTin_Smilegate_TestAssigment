package com.example.daotrungtin_smilegate_testassigment.dto;

import com.example.daotrungtin_smilegate_testassigment.entity.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GameNameDto(
        @NotNull Language language,
        @NotBlank String value
) {}