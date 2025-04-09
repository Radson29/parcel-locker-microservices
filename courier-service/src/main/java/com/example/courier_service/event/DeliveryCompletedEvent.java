package com.example.courier_service.event;

import lombok.Data;

@Data
public class DeliveryCompletedEvent {
    private Long deliveryId;
    private Long courierId;
    private String status;
}