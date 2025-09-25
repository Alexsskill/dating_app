package com.example.dating_app.service;

import com.example.dating_app.dto.LikeResponseDTO;
import com.example.dating_app.exception.LikeAlreadyExistException;
import com.example.dating_app.exception.LikeNotFoundException;
import com.example.dating_app.exception.UserNotFoundException;
import com.example.dating_app.model.Like;
import com.example.dating_app.model.User;
import com.example.dating_app.repository.LikeRepository;
import com.example.dating_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    public void like(Long targetUserId) {
        User currentUser = currentUserService.findUser();
        User targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new UserNotFoundException(targetUserId));

        if (likeRepository.existsBySenderIdAndReceiverId(currentUser.getId(), targetUser.getId())) {
            throw new LikeAlreadyExistException();
        }
        Like like = new Like();
        like.setSender(currentUser);
        like.setReceiver(targetUser);
        likeRepository.save(like);
    }

    public List<LikeResponseDTO> getLikeUsers() {
        User currentUser = currentUserService.findUser();
        List<Like> likes = likeRepository.getLikeUsers(currentUser.getId());

        return likes.stream()
                .map(this::convertToLikeResponseDTO)
                .toList();
    }

    private LikeResponseDTO convertToLikeResponseDTO(Like like) {
        User receiver = like.getReceiver(); // получатель лайка

        return LikeResponseDTO.builder()
                .id(like.getId())
                .userId(receiver.getId())
                .userName(receiver.getFirstName())
                .userAge(receiver.getAge())
                .userCity(receiver.getCity())
                .userGender(receiver.getGender() != null ? receiver.getGender().name() : null)
                .userPhone(receiver.getPhone()) // Добавьте
                .lookingFor(receiver.getLookingFor() != null ? receiver.getLookingFor().name() : null) // Добавьте
                .build();
    }

    public void unlike(Long likeId) {
        Like like = likeRepository.findById(likeId).orElseThrow(LikeNotFoundException::new);
        likeRepository.delete(like);
    }
}


