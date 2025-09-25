package com.example.dating_app.specification;

import com.example.dating_app.dto.RecommendationFilterDTO;
import com.example.dating_app.enums.LookingFor;
import com.example.dating_app.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Objects;

public class UserSpecification {
    public static Specification<User> hasGender(String gender) {
        return (root, query, criteriaBuilder) ->
                gender == null ? null : criteriaBuilder.equal(root.get("gender"), gender);
    }

    public static Specification<User> hasMinAge(Integer minAge) {
        return (root, query, criteriaBuilder) ->
                minAge == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("age"), minAge);
    }

    public static Specification<User> hasMaxAge(Integer maxAge) {
        return (root, query, criteriaBuilder) ->
                maxAge == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("age"), maxAge);
    }

    public static Specification<User> hasCity(String city) {
        return (root, query, criteriaBuilder) ->
                city == null ? null : criteriaBuilder.equal(root.get("city"), city);
    }

    // 🔥 Новая спецификация для поля enum
    public static Specification<User> hasLookingFor(String lookingFor) {
        return (root, query, criteriaBuilder) ->
                lookingFor == null ? null : criteriaBuilder.equal(root.get("lookingFor"), lookingFor);
    }

    // Собрать всё вместе
    @SafeVarargs
    private static Specification<User> combine(Specification<User>... specs) {
        return Arrays.stream(specs)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse((root, query, cb) -> null);
    }

    public static Specification<User> createSpecification (RecommendationFilterDTO recommendationFilterDTO) {
        return UserSpecification.combine(
                UserSpecification.hasGender(recommendationFilterDTO.getGender()),
                UserSpecification.hasMinAge(recommendationFilterDTO.getMinAge()),
                UserSpecification.hasMaxAge(recommendationFilterDTO.getMaxAge()),
                UserSpecification.hasCity(recommendationFilterDTO.getCity()),
                UserSpecification.hasLookingFor(recommendationFilterDTO.getLookingFor())
        );
    }
}
