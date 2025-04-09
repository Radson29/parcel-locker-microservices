package com.example.courier_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {

    public static final String DELIVERY_STARTED_QUEUE = "delivery.started";
    public static final String DELIVERY_COMPLETED_QUEUE = "delivery.completed";

    @Bean
    public Queue deliveryStartedQueue() {
        return new Queue(DELIVERY_STARTED_QUEUE, false);
    }
    @Bean
    public Queue deliveryCompletedQueue() {
        return new Queue(DELIVERY_COMPLETED_QUEUE, false);
    }
}
