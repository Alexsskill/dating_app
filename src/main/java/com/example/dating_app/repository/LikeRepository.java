package com.example.dating_app.repository;

import com.example.dating_app.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    @Query("SELECT l FROM Like l WHERE l.sender.id = :senderId")
    List<Like> getLikeUsers(@Param("senderId") Long senderId);

}
