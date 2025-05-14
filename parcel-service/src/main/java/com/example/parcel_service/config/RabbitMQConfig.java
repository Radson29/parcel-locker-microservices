package com.example.parcel_service.config;
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

    public static final String PARCEL_CREATED_QUEUE = "parcel.created";
    public static final String PARCEL_STATUS_UPDATED_QUEUE = "parcel.status.updated";
    public static final String EXCHANGE = "parcel.exchange";

    @Bean
    public Queue parcelCreatedQueue() {
        return new Queue(PARCEL_CREATED_QUEUE, false);
    }

    @Bean
    public Queue parcelStatusUpdatedQueue() {
        return new Queue(PARCEL_STATUS_UPDATED_QUEUE, false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding parcelStatusBinding(Queue parcelStatusUpdatedQueue, DirectExchange exchange) {
        return BindingBuilder.bind(parcelStatusUpdatedQueue)
                .to(exchange)
                .with(PARCEL_STATUS_UPDATED_QUEUE);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }
}
