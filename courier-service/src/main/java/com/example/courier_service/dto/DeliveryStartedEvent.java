package com.example.courier_service.dto;

import lombok.Data;

@Data
public class DeliveryStartedEvent {
    private Long deliveryId;
    private Long courierId;
    private Long parcelId;
    private String status;
}