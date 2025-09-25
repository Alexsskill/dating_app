package com.example.dating_app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LookingFor {
    FRIENDSHIP ("Дружба"),
    SERIOUS_RELATIONSHIP ("Серьёзные отношения"),
    FAMILY("Семья"),
    TALKING("Общение");

    private final String displayName;
}
