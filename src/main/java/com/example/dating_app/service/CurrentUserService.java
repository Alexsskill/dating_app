package com.example.dating_app.service;

import com.example.dating_app.dto.UserResponseDTO;
import com.example.dating_app.dto.UserUpdateDTO;
import com.example.dating_app.exception.UserAlreadyExistException;
import com.example.dating_app.exception.UserNotFoundException;
import com.example.dating_app.mapper.UserMapper;
import com.example.dating_app.model.User;
import com.example.dating_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User findUser() {

        return userRepository.findById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())
                .orElseThrow(() -> new UserNotFoundException(findUser().getId()));

    }

    public UserResponseDTO getCurrentUser() {

        User currentUser = findUser();

        return userMapper.toUserResponseDTO(currentUser);
    }

    @Transactional
    public UserResponseDTO update(UserUpdateDTO UserUpdateDTO) {

        User currentUser = findUser();

        if (userRepository.existsByPhone(UserUpdateDTO.getPhone())) {
            throw new UserAlreadyExistException(UserUpdateDTO.getPhone());
        }

        userMapper.updateUserFromDTO(UserUpdateDTO, currentUser);

        return userMapper.toUserResponseDTO(userRepository.save(currentUser));
    }
}
