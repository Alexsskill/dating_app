package com.example.dating_app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(MatchId.class)
public class Match {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "liked_user_id")
    private User likedUser;

    private LocalDateTime matchedAt = LocalDateTime.now();
}