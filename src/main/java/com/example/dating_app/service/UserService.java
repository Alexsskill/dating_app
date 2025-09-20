package com.example.dating_app.service;

import com.example.dating_app.dto.RecommendationFilterDTO;
import com.example.dating_app.dto.UserResponseDTO;
import com.example.dating_app.enums.UserRole;
import com.example.dating_app.exception.UserAlreadyExistException;
import com.example.dating_app.mapper.UserMapper;
import com.example.dating_app.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.dating_app.exception.UserNotFoundException;
import com.example.dating_app.model.User;
import com.example.dating_app.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    private UserDetails getByUsername(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException(phone));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User signUp(User user) {
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new UserAlreadyExistException(user.getPhone());
        }
        user.setRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<UserResponseDTO> findAll(RecommendationFilterDTO filter) {
        Specification<User> specification = UserSpecification.createSpecification(filter);
        return userRepository.findAll(specification).stream().map(userMapper::toUserResponseDTO).toList();
    }
}