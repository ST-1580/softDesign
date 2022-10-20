package com.st1580.ppo.lab2.configs;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.st1580.ppo.lab2.statistics.StatisticsDomain;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, StatisticsDomain> {

    @Override
    public StatisticsDomain convert(String source) {
        try {
            return StatisticsDomain.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
