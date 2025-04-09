package com.example.delivery_service.config;


import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DELIVERY_STARTED_QUEUE = "delivery.started";
    public static final String DELIVERY_COMPLETED_QUEUE = "delivery.completed";
    public static final String EXCHANGE = "parcel.exchange";

    @Bean
    public Queue deliveryStartedQueue() {
        return new Queue(DELIVERY_STARTED_QUEUE, false);
    }

    @Bean
    public Queue deliveryCompletedQueue() {
        return new Queue(DELIVERY_COMPLETED_QUEUE, false);
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
    public Binding deliveryStartedBinding(Queue deliveryStartedQueue, DirectExchange parcelExchange) {
        return BindingBuilder.bind(deliveryStartedQueue).to(parcelExchange).with(DELIVERY_STARTED_QUEUE);
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
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
