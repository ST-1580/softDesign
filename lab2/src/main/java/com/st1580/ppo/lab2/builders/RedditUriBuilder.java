package com.st1580.ppo.lab2.builders;

import java.net.URI;
import java.net.URISyntaxException;

public class RedditUriBuilder implements UriBuilder {

    @Override
    public URI buildURI(int hours, String tag){
        final StringBuilder uri = new StringBuilder("https://api.pushshift.io/reddit/search/submission/");
        uri.append("?")
            .append("sort_type=").append("created_utc")
            .append("&after=").append(hours).append("h")
            .append("&q=").append(tag)
            .append("&size=").append(250);

        try {
            return new URI(uri.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Can't create reddit uri");
        }
    }
}
