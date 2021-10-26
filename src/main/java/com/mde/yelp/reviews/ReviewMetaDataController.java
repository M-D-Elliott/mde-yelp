package com.mde.yelp.reviews;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mde.yelp.reviews.model.ReviewDataRaw;
import com.mde.yelp.reviews.model.ReviewMetaData;
import com.mde.yelp.vision.VisionService;
import jPlus.generic.Tuple2;
import jPlus.util.ConsoleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/api/reviews")
public class ReviewMetaDataController {

    @Autowired
    public ReviewService reviewService;

    @Autowired
    public VisionService visionService;

    private final static ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = GET, value = "/{businessID}")
    public @ResponseBody
    ResponseEntity<List<ReviewMetaData>> get(@PathVariable String businessID) {

        final List<ReviewDataRaw> reviewDataRawList = reviewService.get(businessID);

        final List<String> reviewUserProfileImages = reviewDataRawList.stream()
                .map(ReviewDataRaw::getUserImageURL)
                .collect(Collectors.toList());

        final List<Tuple2<String, String>> sorrowAndJoyList = visionService.getSorrowAndJoy(reviewUserProfileImages);

        final List<ReviewMetaData> reviewMetaDataList = new ArrayList<>();

        for (int i = 0; i < reviewDataRawList.size(); i++) {
            final Tuple2<String, String> sorrowAndJoy = sorrowAndJoyList.get(i);
            reviewMetaDataList.add(new ReviewMetaData(reviewDataRawList.get(i), sorrowAndJoy.a, sorrowAndJoy.b));
        }

        printReviewMetaData(reviewMetaDataList);

        return ResponseEntity
                .ok()
                .contentType(MediaTypes.HAL_JSON)
                .body(reviewMetaDataList);
    }

    private void printReviewMetaData(List<ReviewMetaData> reviewMetaData) {
        try {
            System.out.println(ConsoleUtils.encaseInBanner("WRITING OUTPUT AS JSON", 1));
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reviewMetaData));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
