package com.st1580.ppo.lab2.statistics;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface StatisticsService {

    @GetMapping("statistics/{domain}/daily/{tag}")
    default List<Long> getPeriodStatistics(@PathVariable("domain") @NonNull StatisticsDomain domain,
                                           @PathVariable("tag") @NonNull String tag) {
        return getPeriodStatistics(domain, 24, tag);
    }

    @GetMapping("statistics/{domain}/{hours}/{tag}")
    List<Long> getPeriodStatistics(@PathVariable("domain") @NonNull StatisticsDomain domain,
                                   @PathVariable("hours") int hours,
                                   @PathVariable("tag") @NonNull String tag);
}
