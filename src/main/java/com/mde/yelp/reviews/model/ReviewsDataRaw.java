package com.mde.yelp.reviews.model;

import lombok.Data;

import java.util.List;

@Data
public class ReviewsDataRaw {
    private List<ReviewDataRaw> reviews;
    private int total;
    private String[] possible_languages;
}
