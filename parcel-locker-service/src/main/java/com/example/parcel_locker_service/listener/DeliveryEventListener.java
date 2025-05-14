package com.example.parcel_locker_service.listener;

import com.example.parcel_locker_service.config.RabbitMQConfig;

import com.example.parcel_locker_service.event.DeliveryCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.example.parcel_locker_service.client.ParcelServiceClient;
import com.example.parcel_locker_service.config.RabbitMQConfig;
import com.example.parcel_locker_service.event.DeliveryCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryEventListener {

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.DELIVERY_COMPLETED_QUEUE)
    public void handleDeliveryCompletedEvent(DeliveryCompletedEvent event) {
        log.info("Received DeliveryCompletedEvent: {}", event);

        Map<String, Object> message = Map.of(
                "parcelId", event.getParcelId(),
                "status", "DELIVERED_TO_LOCKER"
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.PARCEL_STATUS_UPDATED_QUEUE,
                message
        );

        log.info("Sent parcel status update for parcel {}", event.getParcelId());
    }
}
