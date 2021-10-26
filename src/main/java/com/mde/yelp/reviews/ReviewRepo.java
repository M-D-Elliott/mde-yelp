package com.mde.yelp.reviews;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mde.yelp.reviews.model.ReviewDataRaw;
import com.mde.yelp.reviews.model.ReviewsDataRaw;
import jPlus.util.Resources;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepo {

    private final static String YELP_KEY = "BEARER " + Resources.read("appInfo").get(0).split("=")[1];
    private final static String ENDPOINT_RAW = Resources.read("appInfo").get(1).split("=")[1].replace("BUSINESS_ID_FOR_ENDPOINT", "%s");

    private final static ObjectMapper mapper = new ObjectMapper();

    public List<ReviewDataRaw> get(String businessID) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(ENDPOINT_RAW, businessID)))
                .header("Authorization", YELP_KEY)
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();

        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return mapper.readValue(response.body(), ReviewsDataRaw.class).getReviews();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
