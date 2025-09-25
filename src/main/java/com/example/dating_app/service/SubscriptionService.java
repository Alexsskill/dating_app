package com.example.dating_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dating_app.exception.SubscriptionException;
import com.example.dating_app.model.Subscription;
import com.example.dating_app.model.User;
import com.example.dating_app.repository.SubscriptionRepository;
import com.example.dating_app.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Transactional
    public void upgradeToPremium(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SubscriptionException("Пользователь не найден"));

        Subscription sub = subscriptionRepository.findByUserId(userId).orElse(new Subscription());
        if (sub.isActive()) {
            throw new SubscriptionException("У вас уже активная подписка");
        }

        sub.setUser(user);
        sub.setType("PREMIUM");
        sub.setStartDate(LocalDateTime.now());
        sub.setEndDate(LocalDateTime.now().plusDays(30));
        sub.setActive(true);

        subscriptionRepository.save(sub);
    }
}
