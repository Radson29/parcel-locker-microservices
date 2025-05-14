package com.example.courier_service.listener;

import com.example.courier_service.event.DeliveryCompletedEvent;
import com.example.courier_service.service.CourierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryCompletedListener {

    private final CourierService courierService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "delivery.completed")
    public void handleDeliveryCompleted(String message) {
        try {
            DeliveryCompletedEvent event = objectMapper.readValue(message, DeliveryCompletedEvent.class);
            log.info(" Received DeliveryCompleted event: {}", event);

            courierService.updateCourierAvailability(event.getCourierId(), true);
            log.info(" Courier ID {} marked as available", event.getCourierId());

        } catch (Exception e) {
            log.error(" Error processing DeliveryCompleted event", e);
        }
    }
}
