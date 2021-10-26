package com.mde.yelp.reviews.model;

import com.mde.yelp.users.User;
import lombok.Data;

@Data
public class ReviewDataRaw {
    private String id;
    private String url;
    private String text;
    private int rating;
    private String time_created;
    private User user;

    public ReviewDataRaw() {
    }

    public ReviewDataRaw(String id, String url, String text, int rating, String time_created, User user) {
        this.id = id;
        this.url = url;
        this.text = text;
        this.rating = rating;
        this.time_created = time_created;
        this.user = user;
    }

    public String getUserImageURL() {
        return user.getImage_url();
    }
}
