package com.example.dating_app.service;

import com.example.dating_app.dto.SignInRequest;
import com.example.dating_app.dto.SignUpRequest;
import com.example.dating_app.mapper.UserMapper;
import com.example.dating_app.model.User;
import com.example.dating_app.security.JwtAuthResponse;
import com.example.dating_app.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public JwtAuthResponse signUp(SignUpRequest signUpRequest) {
        User user = userMapper.toUser(signUpRequest);
        userService.signUp(user);
        var TOKEN = jwtService.generateToken(user);
        return new JwtAuthResponse(TOKEN);
    }

    public JwtAuthResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getPhone(), signInRequest.getPassword()
        ));
        var user = userService.userDetailsService().loadUserByUsername(signInRequest.getPhone());
        var TOKEN = jwtService.generateToken(user);
        return new JwtAuthResponse(TOKEN);
    }
}
