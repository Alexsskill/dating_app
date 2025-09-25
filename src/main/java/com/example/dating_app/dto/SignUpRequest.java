package com.example.dating_app.dto;

import com.example.dating_app.enums.Gender;
import com.example.dating_app.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String firstName;
    private String city;
    private Integer age;
    private String phone;
    private String password;
    private Gender gender;
    private UserRole role;
}
