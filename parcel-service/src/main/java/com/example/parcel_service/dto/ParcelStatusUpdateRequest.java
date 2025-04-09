package com.example.parcel_service.dto;


import com.example.parcel_service.entity.ParcelStatus;
import lombok.Data;

@Data
public class ParcelStatusUpdateRequest {
    private ParcelStatus status;
}