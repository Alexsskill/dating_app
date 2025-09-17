package com.example.dating_app.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String bio;
    private String city;
    private String gender;
}