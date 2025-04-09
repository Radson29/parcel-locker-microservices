package com.example.parcel_locker_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DELIVERY_COMPLETED_QUEUE = "delivery.completed";
    public static final String PARCEL_CREATED_QUEUE = "parcel.created";
    public static final String EXCHANGE = "parcel.exchange"; // <-- Dodaj to

    @Bean
    public Queue deliveryCompletedQueue() {
        return new Queue(DELIVERY_COMPLETED_QUEUE, false);
    }

    @Bean
    public Queue parcelCreatedQueue() {
        return new Queue(PARCEL_CREATED_QUEUE, false);
    }

    @Bean
    public DirectExchange parcelExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding deliveryCompletedBinding(Queue deliveryCompletedQueue, DirectExchange parcelExchange) {
        return BindingBuilder.bind(deliveryCompletedQueue).to(parcelExchange).with(DELIVERY_COMPLETED_QUEUE);
    }

    @Bean
    public Binding parcelCreatedBinding(Queue parcelCreatedQueue, DirectExchange parcelExchange) {
        return BindingBuilder.bind(parcelCreatedQueue).to(parcelExchange).with(PARCEL_CREATED_QUEUE);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
