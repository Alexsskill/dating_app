package com.example.dating_app.exception;

public class UserAlreadyExistException extends RuntimeException {

    private static final String MESSAGE = "Пользователь с телефоном %s уже зарегистрирован";

    public UserAlreadyExistException(String phone) {
        super(MESSAGE.formatted(phone));
    }
}
