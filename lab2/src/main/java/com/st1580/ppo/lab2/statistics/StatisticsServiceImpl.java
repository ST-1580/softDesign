package com.st1580.ppo.lab2.statistics;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Controller
public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public List<Long> getPeriodStatistics(StatisticsDomain domain, int hours, String tag) {
        final URI uri = domain.buildUri(hours, tag);
        final JSONObject response = send(uri);
        return domain.convertResponse(response, hours);
    }

    protected JSONObject send(URI uri) {
        final RestTemplate template = new RestTemplate();
        final String responseString = template.getForObject(uri, String.class);
        return new JSONObject(responseString);
    }

}
