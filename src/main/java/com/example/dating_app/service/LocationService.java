package com.example.dating_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dating_app.exception.UserNotFoundException;
import com.example.dating_app.model.Location;
import com.example.dating_app.model.User;
import com.example.dating_app.repository.LocationRepository;
import com.example.dating_app.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void updateLocation(Long userId, Double lat, Double lon, String city) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Location location = locationRepository.findByUserId(userId)
                .orElseGet(() -> Location.builder().user(user).build());

        location.setLatitude(lat);
        location.setLongitude(lon);
        location.setCity(city);
        locationRepository.save(location);
    }
}
