package com.example.delivery_service.publisher;

import com.example.delivery_service.config.RabbitMQConfig;
import com.example.delivery_service.event.DeliveryCompletedEvent;
import com.example.delivery_service.event.DeliveryStartedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishDeliveryStartedEvent(DeliveryStartedEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.DELIVERY_STARTED_QUEUE, event);
    }

    public void publishDeliveryCompletedEvent(DeliveryCompletedEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.DELIVERY_COMPLETED_QUEUE, event);
    }

}