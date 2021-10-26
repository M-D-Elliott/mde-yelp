package com.mde.yelp.vision.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class VisionRequest extends JSONObject {

    public VisionRequest(String uri) {
        final JSONObject featuresObject = new JSONObject();
        featuresObject.put("maxResults", 10);
        featuresObject.put("type", "FACE_DETECTION");
        final JSONArray featuresArray = new JSONArray();
        featuresArray.add(featuresObject);
        put("features", featuresArray);

        final JSONObject imageObject = new JSONObject();
        final JSONObject sourceObject = new JSONObject();
        sourceObject.put("imageUri", uri);
        imageObject.put("source", sourceObject);
        put("image", imageObject);
    }
}
