package com.example.dating_app.repository;

import com.example.dating_app.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    java.util.Optional<Location> findByUserId(Long userId);
}
