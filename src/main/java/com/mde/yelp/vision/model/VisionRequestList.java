package com.mde.yelp.vision.model;

import jPlus.util.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

@SuppressWarnings("unchecked")
public class VisionRequestList extends JSONObject {
    public VisionRequestList(List<String> uris) {
        final JSONArray requests = new JSONArray();
        for (String uri : uris) if (StringUtils.hasContent(uri)) requests.add(new VisionRequest(uri));
        put("requests", requests);
    }
}
