package com.mde.yelp.reviews;

import com.mde.yelp.reviews.model.ReviewDataRaw;
import jPlus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepo reviewRepo;

    public List<ReviewDataRaw> get(String businessID) {
        return StringUtils.hasContent(businessID)
                ? reviewRepo.get(businessID)
                : new ArrayList<>();
    }
}
