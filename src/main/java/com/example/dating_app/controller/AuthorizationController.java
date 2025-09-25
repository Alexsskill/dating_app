package com.example.dating_app.controller;

import com.example.dating_app.dto.SignInRequest;
import com.example.dating_app.dto.SignUpRequest;
import com.example.dating_app.security.JwtAuthResponse;
import com.example.dating_app.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @PostMapping("sign-up")
    public JwtAuthResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return authorizationService.signUp(signUpRequest);
    }

    @PostMapping("sign-in")
    public JwtAuthResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authorizationService.signIn(signInRequest);
    }
}
