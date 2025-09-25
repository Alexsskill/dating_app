package com.example.dating_app.dto;

import com.example.dating_app.enums.LookingFor;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private Integer age;
    private String bio;
    private String city;
    private String gender;
    private LookingFor lookingFor;
}