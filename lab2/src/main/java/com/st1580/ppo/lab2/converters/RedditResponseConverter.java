package com.st1580.ppo.lab2.converters;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RedditResponseConverter extends ResponseConverter {

    @Override
    public List<Long> convertResponse(JSONObject json, int hours) {
        if (!json.has("data")) {
            throw new RuntimeException("JSON doesn't have needed key");
        }

        JSONArray data = json.getJSONArray("data");
        final List<Long> timeStamps = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            long ts = data.getJSONObject(i).getLong("created_utc");
            timeStamps.add(ts);
        }

        return super.getStatistics(timeStamps, System.currentTimeMillis(), hours);
    }
}
