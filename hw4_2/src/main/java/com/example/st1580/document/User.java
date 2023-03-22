package com.example.st1580.document;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User {
    @Id
    private String id;
    private final String name;
    private final CurrencyType currencyType;

    public User(String name, CurrencyType currencyType) {
        this.name = name;
        this.currencyType = currencyType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && currencyType == user.currencyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, currencyType);
    }

    public enum CurrencyType {
        RUB, USD, EUR
    }
}

