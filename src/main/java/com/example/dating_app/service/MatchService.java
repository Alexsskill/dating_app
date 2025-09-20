package com.example.dating_app.service;

import com.example.dating_app.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dating_app.exception.UserNotFoundException;
import com.example.dating_app.model.Match;
import com.example.dating_app.model.User;
import com.example.dating_app.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    @Transactional
    public String likeUser(Long targetUserId) {
        User currentUser = getCurrentUser();
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new UserNotFoundException(targetUserId));

        if (matchRepository.existsByUser_IdAndLikedUser_Id(currentUser.getId(), targetUser.getId())) {
            return "Вы уже лайкали этого пользователя";
        }

        Match match = new Match();
        match.setUser(currentUser);
        match.setLikedUser(targetUser);
        matchRepository.save(match);

        // Проверка на взаимный лайк
        if (matchRepository.existsByUser_IdAndLikedUser_Id(targetUser.getId(), currentUser.getId())) {
            return "У вас взаимная симпатия!";
        }

        return "Лайк отправлен";
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}