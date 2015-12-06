package com.ss.bth;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Samuil on 05-12-2015
 */
@Component
public class UserRatingReceiver {

    Logger logger = Logger.getLogger(UserRatingReceiver.class);

    @RabbitListener(queues = RatingQueueConfiguration.USER_RATING_QUEUE)
    public void receiveUserRatingUpdate(UserRatingDTO ratingDTO) {
        logger.info("Received: " + ratingDTO.getId() + " => " + ratingDTO.getRating());
    }

}
