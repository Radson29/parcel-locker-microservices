package com.example.courier_service.exception;

public class NoAvailableCourierException extends RuntimeException {
    public NoAvailableCourierException(String message) {
        super(message);
    }
}
