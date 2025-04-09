package com.example.parcel_locker_service.listener;

import com.example.parcel_locker_service.config.RabbitMQConfig;

import com.example.parcel_locker_service.event.DeliveryCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.parcel_locker_service.client.ParcelServiceClient;
import com.example.parcel_locker_service.config.RabbitMQConfig;
import com.example.parcel_locker_service.event.DeliveryCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryEventListener {

    private final ParcelServiceClient parcelServiceClient;

    @RabbitListener(queues = RabbitMQConfig.DELIVERY_COMPLETED_QUEUE)
    public void handleDeliveryCompletedEvent(DeliveryCompletedEvent event) {
        log.info("Received DeliveryCompletedEvent: {}", event);

        log.info("➡️ Calling ParcelServiceClient to update parcel status to DELIVERED_TO_LOCKER for parcelId: {}", event.getParcelId());
        parcelServiceClient.updateParcelStatus(event.getParcelId(), "DELIVERED_TO_LOCKER");
        log.info("✅ ParcelServiceClient call completed for parcelId: {}", event.getParcelId());
    }
}
