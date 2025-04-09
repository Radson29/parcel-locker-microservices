package com.example.parcel_locker_service.exception;

public class LockerFullException extends RuntimeException {
    public LockerFullException(String message) {
        super(message);
    }
}
