package com.example.parcel_locker_service.dto;

import lombok.Data;

@Data
public class LockerRequest {
    private String location;
    private int smallSlots;
    private int mediumSlots;
    private int largeSlots;
}
