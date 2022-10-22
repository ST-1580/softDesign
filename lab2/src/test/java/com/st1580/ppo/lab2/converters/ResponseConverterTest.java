package com.st1580.ppo.lab2.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ResponseConverterTest {

    final static ResponseConverter converter = spy(ResponseConverter.class);

    @BeforeAll
    public static void setUp() {
        final long ONE_HOUR_TS = 1000L * 3600L;

        when(converter.convertResponse(any(), anyInt()))
            .thenAnswer(info -> {
                JSONObject json = info.getArgument(0, JSONObject.class);
                int hours = info.getArgument(1);

                JSONArray array = json.getJSONArray("data");
                List<Long> testedTsMillis = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    testedTsMillis.add(array.getLong(i) * ONE_HOUR_TS);
                }

                return converter.getStatistics(testedTsMillis, hours * ONE_HOUR_TS - 1, hours);
            });
    }


    @Test
    public void statisticsEmptyList() {
        final Map<String, List<Long>> data = new HashMap<>();
        data.put("data", List.of());

        List<Long> actual = converter.convertResponse(new JSONObject(data), 2);
        List<Long> expected = List.of(0L, 0L);

        assertEquals(expected, actual);
    }

    @Test
    public void statisticsDuplicateHours() {
        final Map<String, List<Long>> data = new HashMap<>();
        data.put("data", List.of(1L, 1L));

        List<Long> actual = converter.convertResponse(new JSONObject(data), 2);
        List<Long> expected = List.of(0L, 2L);

        assertEquals(expected, actual);
    }

    @Test
    public void statisticsHoursOutsidePeriodLeft() {
        final Map<String, List<Long>> data = new HashMap<>();
        data.put("data", List.of(-1L, 1L));

        List<Long> actual = converter.convertResponse(new JSONObject(data), 2);
        List<Long> expected = List.of(0L, 1L);

        assertEquals(expected, actual);
    }

    @Test
    public void statisticsHoursOutsidePeriodRight() {
        final Map<String, List<Long>> data = new HashMap<>();
        data.put("data", List.of(1L, 3L));

        List<Long> actual = converter.convertResponse(new JSONObject(data), 2);
        List<Long> expected = List.of(0L, 1L);

        assertEquals(expected, actual);
    }

    @Test
    public void statisticsAllCases() {
        final Map<String, List<Long>> data = new HashMap<>();
        data.put("data", List.of(-1L, -1L, 0L, 1L, 1L, 3L, 3L));

        List<Long> actual = converter.convertResponse(new JSONObject(data), 2);
        List<Long> expected = List.of(1L, 2L);

        assertEquals(expected, actual);
    }
}
