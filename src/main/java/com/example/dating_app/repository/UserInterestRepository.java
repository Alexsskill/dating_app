package com.example.dating_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.dating_app.model.UserInterest;
import com.example.dating_app.model.UserInterestId;

import java.util.List;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, UserInterestId> {
    List<UserInterest> findByUser_Id(Long userId);
    boolean existsByUser_IdAndInterest_Id(Long userId, Long interestId);
}