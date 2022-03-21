package com.endava.tmd.soj.schedulematcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Tests for the Schedule class")
class ScheduleTest {

    private Schedule schedule;

    @BeforeEach
    void setUp() {
        this.schedule = new Schedule();
    }

    @Test
    @DisplayName("An exception gets thrown if the time interval has already been marked as busy")
    void timeIntervalHasAlreadyBeenAdded() {
        schedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16));
        assertThatThrownBy(() -> schedule
                .addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This time interval has already been marked as busy for the given day");
    }

    @Test
    @DisplayName("Adding a new busy time interval works properly if there is no overlap")
    void addingTimeIntervalWorksProperly() {
        var firstBusyTimeInterval = new TimeInterval(15, 18);
        var secondBusyTimeInterval = new TimeInterval(20, 21);
        schedule.addBusyTimeInterval(Day.MONDAY, firstBusyTimeInterval);
        schedule.addBusyTimeInterval(Day.MONDAY, secondBusyTimeInterval);

        var timeIntervals = schedule
                .getBusyTimeIntervals(Day.MONDAY);

        timeIntervals.ifPresentOrElse(intervals -> assertThat(intervals)
                        .containsExactly(firstBusyTimeInterval, secondBusyTimeInterval),
                () -> fail("The schedule should contain time intervals for Monday"));
    }

    @Test
    @DisplayName("Adding the same time interval for two different days works as expected")
    void addingTheSameTimeIntervalForDifferentDays() {
        var firstBusyTimeInterval = new TimeInterval(15, 18);
        var secondBusyTimeInterval = new TimeInterval(20, 21);

        schedule.addBusyTimeInterval(Day.MONDAY, firstBusyTimeInterval);
        schedule.addBusyTimeInterval(Day.MONDAY, secondBusyTimeInterval);
        schedule.addBusyTimeInterval(Day.FRIDAY, firstBusyTimeInterval);
        schedule.addBusyTimeInterval(Day.FRIDAY, secondBusyTimeInterval);

        var mondayBusyTimeIntervals = schedule.getBusyTimeIntervals(Day.MONDAY);
        var fridayBusyTimeIntervals = schedule.getBusyTimeIntervals(Day.FRIDAY);

        mondayBusyTimeIntervals.ifPresentOrElse(intervals -> assertThat(intervals)
                        .containsExactly(firstBusyTimeInterval, secondBusyTimeInterval),
                () -> fail("The schedule should contain time intervals for Monday"));

        fridayBusyTimeIntervals.ifPresentOrElse(intervals -> assertThat(intervals)
                        .containsExactly(firstBusyTimeInterval, secondBusyTimeInterval),
                () -> fail("The schedule should contain time intervals for Monday"));
    }

    @Test
    @DisplayName("An exception gets thrown if the starting hour matches another busy interval's starting hour")
    void startingHourOverlaps() {
        schedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(17, 18));
        assertThatThrownBy(() -> schedule
                .addBusyTimeInterval(Day.MONDAY, new TimeInterval(17, 20)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This time interval overlaps with another time interval that has been marked as busy for the given day");
    }

    @Test
    @DisplayName("An exception gets thrown if the time interval's start hour is in a marked time interval")
    void timeIntervalsOverlap() {
        schedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 17));
        assertThatThrownBy(() -> schedule
                .addBusyTimeInterval(Day.MONDAY, new TimeInterval(16, 18)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This time interval overlaps with another time interval that has been marked as busy for the given day");
    }

    @Test
    @DisplayName("Adding a time interval works properly if the ending hours overlap")
    void endingHourOverlaps() {
        var firstBusyTimeInterval = new TimeInterval(16, 18);
        var secondBusyTimeInterval = new TimeInterval(18, 20);
        schedule.addBusyTimeInterval(Day.FRIDAY, firstBusyTimeInterval);
        schedule.addBusyTimeInterval(Day.FRIDAY, secondBusyTimeInterval);

        var busyTimeIntervals = schedule.getBusyTimeIntervals(Day.FRIDAY);

        busyTimeIntervals.ifPresentOrElse(timeIntervals -> assertThat(timeIntervals)
                        .containsExactly(firstBusyTimeInterval, secondBusyTimeInterval),
                () -> fail("The schedule should contain time intervals for Friday"));
    }

    @Test
    @DisplayName("An exception gets thrown if the time interval is inside another time interval")
    void timeIntervalIsInsideAnotherTimeInterval() {
        schedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(16, 20));
        assertThatThrownBy(() -> schedule
                .addBusyTimeInterval(Day.MONDAY, new TimeInterval(17, 18)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This time interval overlaps with another time interval that has been marked as busy for the given day");
    }
}