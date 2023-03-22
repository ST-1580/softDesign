package com.example.st1580.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDto {
    private final String name;
    private final long rublePrice;

    public ProductDto(@JsonProperty("name") String name, @JsonProperty("rublePrice") long rublePrice) {
        this.name = name;
        this.rublePrice = rublePrice;
    }

    public String getName() {
        return name;
    }

    public long getRublePrice() {
        return rublePrice;
    }
}
