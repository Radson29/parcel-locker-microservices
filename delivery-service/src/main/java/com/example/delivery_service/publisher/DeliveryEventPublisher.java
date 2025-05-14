package com.example.delivery_service.publisher;

import com.example.delivery_service.config.RabbitMQConfig;
import com.example.delivery_service.event.DeliveryCompletedEvent;
import com.example.delivery_service.event.DeliveryStartedEvent;
import com.example.delivery_service.event.ParcelStatusUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DeliveryEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publishDeliveryStartedEvent(DeliveryStartedEvent event) {
        rabbitTemplate.convertAndSend(
                "parcel.exchange",
                "delivery.started",
                event
        );
    }

    public void publishDeliveryCompletedEvent(DeliveryCompletedEvent event) {
        rabbitTemplate.convertAndSend(
                "parcel.exchange",
                "delivery.completed",
                event
        );
    }

    public void publishParcelStatusUpdatedEvent(Long parcelId, String status) {
        Map<String, Object> message = Map.of(
                "parcelId", parcelId,
                "status", status
        );

        rabbitTemplate.convertAndSend(
                "parcel.exchange",
                "parcel.status.updated",
                message
        );
    }
}