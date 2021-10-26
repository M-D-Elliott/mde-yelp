package com.mde.yelp.vision;

import com.mde.yelp.vision.model.VisionRequestList;
import jPlus.generic.Tuple2;
import jPlus.util.Resources;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class VisionService {
    private final static String ENDPOINT =
            Resources.read("appInfo").get(3).split("=")[1]
                    + "key="
                    + Resources.read("appInfo").get(2).split("=")[1];

    private static final JSONParser parser = new JSONParser();

    public List<Tuple2<String, String>> getSorrowAndJoy(List<String> imageURIs) {

        final List<Tuple2<String, String>> result = new ArrayList<>();

        final VisionRequestList requestList = new VisionRequestList(imageURIs);
        final String requestJson = requestList.toJSONString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final JSONObject output = (JSONObject) parser.parse(response.body());
            final JSONArray responses = (JSONArray) output.get("responses");
            if(responses != null){
                for (Object obj : responses) {
                    final JSONObject faceAnnotationsWrapper = (JSONObject) obj;
                    final JSONArray faceAnnotations = (JSONArray) faceAnnotationsWrapper.get("faceAnnotations");
                    if (faceAnnotations.size() > 0) {
                        final JSONObject firstFace = (JSONObject) faceAnnotations.get(0);
                        final Tuple2<String, String> sorrowAndJoy =
                                new Tuple2<>((String) firstFace.get("sorrowLikelihood"), (String) firstFace.get("joyLikelihood"));
                        result.add(sorrowAndJoy);
                    }
                }
            }
        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }

        final int inputSize = imageURIs.size();
        while(result.size() < inputSize) result.add(new Tuple2<>("",""));

        return result;
    }
}
