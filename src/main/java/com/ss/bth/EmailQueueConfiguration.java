package com.ss.bth;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Samuil on 04-12-2015
 */
@Configuration
public class EmailQueueConfiguration extends RabbitMqConfiguration {

    protected final static String ACTIVATION_EMAIL_QUEUE = "ss-activation-email";

    @Bean
    public Queue emailQueue() {
        return new Queue(ACTIVATION_EMAIL_QUEUE, true, false, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(ACTIVATION_EMAIL_QUEUE);
    }

    @Bean
    Binding binding(TopicExchange exchange) {
        return BindingBuilder.bind(emailQueue()).to(exchange).with(ACTIVATION_EMAIL_QUEUE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setRoutingKey(ACTIVATION_EMAIL_QUEUE);
        rabbitTemplate.setQueue(ACTIVATION_EMAIL_QUEUE);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }
}
