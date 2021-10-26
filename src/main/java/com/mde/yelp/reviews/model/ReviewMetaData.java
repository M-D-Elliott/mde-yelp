package com.mde.yelp.reviews.model;

import jPlus.util.StringUtils;
import lombok.Data;

@Data
public class ReviewMetaData {
    private int rating;
    private String date;
    private String reviewer;
    private String sorrowLikelihood;
    private String joyLikelihood;
    private String body;
    private String url;

    public ReviewMetaData() {
    }

    public ReviewMetaData(ReviewDataRaw rawData, String sorrowLikelihood, String joyLikelihood) {
        this.rating = rawData.getRating();
        this.date = rawData.getTime_created();
        this.reviewer = rawData.getUser().getName();
        this.sorrowLikelihood = StringUtils.hasContent(sorrowLikelihood) ? sorrowLikelihood : VISION_API_FAILED;
        this.joyLikelihood = StringUtils.hasContent(joyLikelihood) ? joyLikelihood : VISION_API_FAILED;
        this.body = rawData.getText();
        this.url = rawData.getUrl();
    }

    private static String VISION_API_FAILED = "The vision API returned no result. Check your key?";
}
