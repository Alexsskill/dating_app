package com.example.dating_app.dto;

import com.example.dating_app.enums.Gender;
import com.example.dating_app.enums.LookingFor;
import com.example.dating_app.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String bio;
    private String city;
    private Integer age;
    private String phone;
    private String email;
    private Gender gender;
    private UserRole role;
    private LookingFor lookingFor;
}
