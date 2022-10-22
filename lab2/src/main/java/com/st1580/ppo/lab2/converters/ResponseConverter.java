package com.st1580.ppo.lab2.converters;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class ResponseConverter {

    public List<Long> convertResponse(JSONObject json, int hours) {
        return new ArrayList<>();
    };

    /**
     *
     * @param tagsTsMillis - list sorted in ascending order by ts in millis
     * @return Statistics by hours
     */
    protected List<Long> getStatistics(List<Long> tagsTsMillis, Long nowTs, int hours) {
        final long ONE_HOUR_TS = 1000L * 3600L;
        List<Long> res = new ArrayList<>(Collections.nCopies(hours, 0L));
        long startTs = nowTs - hours * ONE_HOUR_TS;

        int timeSlot = 0;
        for (Long tagsT : tagsTsMillis) {
            if (tagsT < startTs + timeSlot * ONE_HOUR_TS) {
                continue;
            }
            if (tagsT >= startTs + (timeSlot + 1) * ONE_HOUR_TS) {
                timeSlot++;
                if (timeSlot >= hours) {
                    break;
                }
            }

            res.set(timeSlot, res.get(timeSlot) + 1);
        }

        return res;
    }
}
