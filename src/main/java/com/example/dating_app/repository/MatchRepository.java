package com.example.dating_app.repository;

import com.example.dating_app.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
    boolean existsByUser_IdAndLikedUser_Id(Long currentUserId, Long targetUser);
}
