package com.example.dating_app.mapper;

import com.example.dating_app.dto.SignUpRequest;
import com.example.dating_app.dto.UserResponseDTO;
import com.example.dating_app.dto.UserUpdateDTO;
import com.example.dating_app.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(SignUpRequest signUpRequest);

    UserResponseDTO toUserResponseDTO(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDTO(UserUpdateDTO userUpdateDTO, @MappingTarget User user);
}
