package com.example.courier_service.listener;

import com.example.courier_service.config.RabbitMQConfig;
import com.example.courier_service.dto.DeliveryStartedEvent;
import com.example.courier_service.service.CourierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryStartedListener {

    private final CourierService courierService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.DELIVERY_STARTED_QUEUE)
    public void handleDeliveryStarted(String message) {
        try {
            DeliveryStartedEvent event = objectMapper.readValue(message, DeliveryStartedEvent.class);
            log.info("Received DeliveryStarted event: {}", event);

            courierService.updateCourierAvailability(event.getCourierId(), false);
            log.info("Courier ID {} marked as unavailable (busy)", event.getCourierId());

        } catch (Exception e) {
            log.error("Error processing DeliveryStarted event", e);
        }
    }
}
