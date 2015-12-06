package com.ss.bth;

/**
 * Created by Samuil on 04-12-2015
 */
public class UserRatingDTO {

    private String id;
    private double rating;

    public UserRatingDTO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
