package com.example.dating_app.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE_FOR_ID = "Пользователь с ID %d не найден";
    private static final String MESSAGE_FOR_PHONE = "Пользователь с номером телефона %s не найден";

    public UserNotFoundException(Long id) {
        super(MESSAGE_FOR_ID.formatted(id));
    }

    public UserNotFoundException(String phone) {
        super(MESSAGE_FOR_PHONE.formatted(phone));
    }
}
