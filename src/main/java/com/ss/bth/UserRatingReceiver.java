package com.ss.bth;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Samuil on 05-12-2015
 */
@Component
public class UserRatingReceiver {

    private final UserService service;

    @Autowired
    UserRatingReceiver(UserService service) {
        this.service = service;
    }

    Logger logger = Logger.getLogger(UserRatingReceiver.class);

    @RabbitListener(queues = RatingQueueConfiguration.USER_RATING_QUEUE)
    public void receiveUserRatingUpdate(UserRatingDAO ratingDTO) {
        logger.info("Received: " + ratingDTO.getId() + " => " + ratingDTO.getRating());
        try {
            User user = service.getRepository().findOne(ratingDTO.getId());
            service.update(service.convertToUpdateDAO(service.convertToDAO(user)));
            logger.info("RATING UPDATED!");
        } catch (Exception e) {
            logger.error("ERROR: " + e.getMessage());
        }
    }

}
