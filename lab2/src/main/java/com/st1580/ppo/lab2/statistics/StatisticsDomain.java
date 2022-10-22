package com.st1580.ppo.lab2.statistics;

import com.st1580.ppo.lab2.builders.RedditUriBuilder;
import com.st1580.ppo.lab2.builders.UriBuilder;
import com.st1580.ppo.lab2.builders.VkUriBuilder;
import com.st1580.ppo.lab2.converters.RedditResponseConverter;
import com.st1580.ppo.lab2.converters.ResponseConverter;
import com.st1580.ppo.lab2.converters.VkResponseConverter;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.List;

public enum StatisticsDomain {
    REDDIT(RedditUriBuilder.class, RedditResponseConverter.class),
    VK(VkUriBuilder.class, VkResponseConverter.class);

    private final Class<? extends UriBuilder> uriBuilderClass;
    private final Class<? extends ResponseConverter> responseConverterClass;

    StatisticsDomain(Class<? extends UriBuilder> uriBuilderClass,
                     Class<? extends ResponseConverter> responseConverterClass) {
        this.uriBuilderClass = uriBuilderClass;
        this.responseConverterClass = responseConverterClass;
    }

    public URI buildUri(int hours, String tag) {
        try {
            return uriBuilderClass.getDeclaredConstructor().newInstance().buildURI(hours, tag);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Can't find build method for " + uriBuilderClass.getName());
        }
    }

    public List<Long> convertResponse(JSONObject json, int hours) {
        try {
            return responseConverterClass.getDeclaredConstructor().newInstance().convertResponse(json, hours);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Can't find convert method for " + uriBuilderClass.getName());
        }
    }
}
