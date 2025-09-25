package com.example.dating_app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum LookingFor {
    FRIENDSHIP,
    SERIOUS_RELATIONSHIP,
    FAMILY,
    TALKING;

//    @JsonCreator
//    public static LookingFor fromString(String value) {
//        if (value == null || value.trim().isEmpty()) {
//            System.out.println("СОСИ ХУЙ");
//            return null;
//        }
//        try {
//            return LookingFor.valueOf(value.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            return null; // или выбросить исключение
//        }
//    }
//    private final String displayName;
}
