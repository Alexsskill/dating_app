package com.example.dating_app.exception;

public class LikeAlreadyExistException extends RuntimeException {
    private static final String MESSAGE = "Like already exist";

    public LikeAlreadyExistException() {
        super(MESSAGE);
    }
}
