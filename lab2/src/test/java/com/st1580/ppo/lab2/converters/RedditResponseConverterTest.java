package com.st1580.ppo.lab2.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class RedditResponseConverterTest {
    final static RedditResponseConverter converter = spy(RedditResponseConverter.class);

    @BeforeAll
    public static void setUp() {
        when(converter.getStatistics(anyList(), anyLong(), anyInt()))
                .thenAnswer(i -> i.getArgument(0));
    }

    private JSONObject createData(List<Long> tsList) {
        List<JSONObject> res = new ArrayList<>();
        final Map<String, Long> createdTs = new HashMap<>();

        for (Long ts : tsList) {
            createdTs.put("created_utc", ts);
            res.add(new JSONObject(createdTs));
        }

        final Map<String, List<JSONObject>> data = new HashMap<>();
        data.put("data", res);

        return new JSONObject(data);
    }

    @Test
    public void convertEmptyJson() {
        try {
           converter.convertResponse(new JSONObject(), 0);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("JSON doesn't have needed key")) {
                return;
            }
        }

        fail();
    }

    @Test
    public void convertEmptyData() {
        List<Long> ts = List.of();

        List<Long> actual = converter.convertResponse(createData(ts), 0);
        List<Long> expected = new ArrayList<>();

        assertEquals(expected, actual);
    }

    @Test
    public void convertData() {
        List<Long> ts = List.of(1L, 2L, 5L);

        List<Long> actual = converter.convertResponse(createData(ts), 0);
        List<Long> expected = List.of(1000L, 2000L, 5000L);

        assertEquals(expected, actual);
    }


}
