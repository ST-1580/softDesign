package stat;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EventStatisticImpl implements EventsStatistic {
    private final Clock clock;
    private final Map<String, Queue<LocalDateTime>> eventToTimes = new HashMap<>();

    public EventStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        eventToTimes.putIfAbsent(name, new ArrayDeque<>());
        eventToTimes.get(name).add(clock.now());
    }

    @Override
    public double getEventStatisticByName(String name) {
        Queue<LocalDateTime> eventTimes = eventToTimes.getOrDefault(name, null);
        if (eventTimes == null) {
            return 0;
        }

        while (eventTimes.peek() != null && eventTimes.peek().isBefore(clock.now().minusHours(1))) {
            eventTimes.poll();
        }

        return ((double) eventTimes.size()) / 60;
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        Map<String, Double> res = new HashMap<>();
        for (String name: eventToTimes.keySet()) {
            double rpm = getEventStatisticByName(name);
            res.put(name, rpm);
        }
        return res;
    }

    @Override
    public void printStatistic() {
        String res = getAllEventStatistic().entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println(res);
    }
}
