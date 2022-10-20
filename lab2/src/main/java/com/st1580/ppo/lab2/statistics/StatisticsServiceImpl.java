package com.st1580.ppo.lab2.statistics;

import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public List<Long> getPeriodStatistics(StatisticsDomain domain, int hours, String tag) {
        return domain.getStatistics(hours, tag);
    }
}
