package com.example.parcel_service.publisher;

import com.example.parcel_service.config.RabbitMQConfig;
import com.example.parcel_service.entity.Parcel;
import com.example.parcel_service.event.ParcelCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParcelEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishParcelCreatedEvent(Parcel parcel) {
        ParcelCreatedEvent event = new ParcelCreatedEvent(
                parcel.getId(),
                parcel.getUserId(),
                String.valueOf(parcel.getLockerId()),
                parcel.getStatus().name(),
                parcel.getSize().name()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.PARCEL_CREATED_QUEUE, event);
    }
}
