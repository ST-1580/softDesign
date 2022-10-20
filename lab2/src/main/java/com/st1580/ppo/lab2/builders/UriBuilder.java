package com.st1580.ppo.lab2.builders;

import org.springframework.lang.NonNull;

import java.net.URI;

public interface UriBuilder {

    URI buildURI(int hours, @NonNull String tag);
}
