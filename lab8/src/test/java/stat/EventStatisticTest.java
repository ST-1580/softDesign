package stat;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventStatisticTest {

    @Test
    void test1() {
        Clock clock = mock(Clock.class);
        EventsStatistic eventsStatistic = new EventStatisticImpl(clock);

        LocalDateTime now = LocalDateTime.now();

        when(clock.now())
                .thenReturn(now)
                .thenReturn(now.plusHours(2));

        eventsStatistic.incEvent("test");
        assertEquals(0.0, eventsStatistic.getEventStatisticByName("test"));
    }

    @Test
    void test2() {
        Clock clock = mock(Clock.class);
        EventsStatistic eventsStatistic = new EventStatisticImpl(clock);

        LocalDateTime now = LocalDateTime.now();

        when(clock.now())
                .thenReturn(now)
                .thenReturn(now.plusMinutes(30));

        for (int i = 0; i < 30; i++) {
            eventsStatistic.incEvent("test");
        }
        assertEquals(0.5, eventsStatistic.getEventStatisticByName("test"));
    }

    @Test
    void test3() {
        Clock clock = mock(Clock.class);
        EventsStatistic eventsStatistic = new EventStatisticImpl(clock);

        LocalDateTime now = LocalDateTime.now();

        when(clock.now())
                .thenReturn(now);

        eventsStatistic.incEvent("test");
        assertEquals(1.0 / 60, eventsStatistic.getEventStatisticByName("test"));
    }
}
