package com.example.dating_app.exception;

public class LikeNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Like not found";

    public LikeNotFoundException() {
        super(MESSAGE);
    }
}
