package com.example.dating_app.model;

import lombok.*;
import java.io.Serializable;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchId implements Serializable {
    private Long user;
    private Long likedUser;
}