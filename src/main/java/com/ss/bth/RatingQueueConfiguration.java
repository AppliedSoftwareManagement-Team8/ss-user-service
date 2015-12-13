package com.ss.bth;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Samuil on 04-12-2015
 */
@Configuration
@EnableRabbit
public class RatingQueueConfiguration extends RabbitMqConfiguration{

    protected final static String USER_RATING_QUEUE = "ss-user-rating";

    @Bean
    public Queue ratingQueue() {
        return new Queue(USER_RATING_QUEUE, true, false, false);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        container.setMessageConverter(jsonRatingMessageConverter());
        return container;
    }

    @Bean
    public MessageConverter jsonRatingMessageConverter()
    {
        final Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper());
        return converter;
    }

    @Bean
    public DefaultClassMapper classMapper()
    {
        DefaultClassMapper typeMapper = new DefaultClassMapper();
        typeMapper.setDefaultType(UserRatingDAO.class);
        return typeMapper;
    }
}
