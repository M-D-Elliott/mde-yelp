package com.mde.yelp.users;

import lombok.Data;

@Data
public class User {
    private String id;
    private String profile_url;
    private String image_url;
    private String name;

    public User() {
    }

    public User(String id, String profile_url, String image_url, String name) {
        this.id = id;
        this.profile_url = profile_url;
        this.image_url = image_url;
        this.name = name;
    }
}
