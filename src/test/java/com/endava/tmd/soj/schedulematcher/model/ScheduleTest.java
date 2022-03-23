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
    @DisplayName("An exception gets thrown if the time interval's starting hour is in a marked time interval")
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

    @Test
    @DisplayName("equals method returns true if the compared schedules contain the same busy interval")
    void equalsReturnsTrueForSameIntervals() {
        schedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(10, 11));
        var otherSchedule = new Schedule();
        otherSchedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(10, 11));
        assertThat(schedule.equals(otherSchedule)).isTrue();
    }

    @Test
    @DisplayName("equals method returns true if schedules contain the same busy intervals for multiple days")
    void equalsReturnsTrueForMultipleIdenticalIntervals() {
        addBusyTimeIntervals(schedule);
        var otherSchedule = new Schedule();
        addBusyTimeIntervals(otherSchedule);
        assertThat(schedule.equals(otherSchedule)).isTrue();
    }

    @Test
    @DisplayName("equals method returns false if schedules contain a different busy time interval for the same day")
    void equalsReturnsFalseForOneDifferentTimeIntervals() {
        schedule.addBusyTimeInterval(Day.TUESDAY, new TimeInterval(15, 16));
        var otherSchedule = new Schedule();
        otherSchedule.addBusyTimeInterval(Day.TUESDAY, new TimeInterval(10, 11));
        assertThat(schedule.equals(otherSchedule)).isFalse();
    }

    @Test
    @DisplayName("equals method returns false if schedules contain different busy time intervals for multiple days")
    void equalsReturnsFalseForMultipleDifferentTimeIntervals() {
        addBusyTimeIntervals(schedule);
        var otherSchedule = new Schedule();
        otherSchedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(11, 12));
        otherSchedule.addBusyTimeInterval(Day.FRIDAY, new TimeInterval(14, 15));
        otherSchedule.addBusyTimeInterval(Day.SATURDAY, new TimeInterval(16, 17));
        otherSchedule.addBusyTimeInterval(Day.SUNDAY, new TimeInterval(10, 11));
        assertThat(schedule.equals(otherSchedule)).isFalse();
    }

    @Test
    @DisplayName("equals method returns true if the compared schedules contain no busy intervals")
    void equalsReturnsTrueForNoTimeIntervals() {
        var otherSchedule = new Schedule();
        assertThat(schedule.equals(otherSchedule)).isTrue();
    }

    @Test
    @DisplayName("equals method returns false if one schedule has no busy time intervals")
    void equalsReturnsFalseIfOneScheduleHasNoBusyTimeIntervals() {
        addBusyTimeIntervals(schedule);
        var otherSchedule = new Schedule();
        assertThat(schedule.equals(otherSchedule)).isFalse();
    }

    @Test
    @DisplayName("equals method returns false is two identical intervals do not have the same color")
    void equalsReturnsFalseForIdenticalIntervalsWithDifferentColors() {
        schedule.addBusyTimeInterval(Day.WEDNESDAY, new TimeInterval(15, 16, IntervalColor.RED));
        var otherSchedule = new Schedule();
        otherSchedule.addBusyTimeInterval(Day.WEDNESDAY, new TimeInterval(15, 16, IntervalColor.YELLOW));
        assertThat(schedule.equals(otherSchedule)).isFalse();
    }

    @Test
    @DisplayName("equals method returns false is two identical intervals do not have the same color")
    void equalsReturnsTrueForIdenticalIntervalsWithSameColors() {
        schedule.addBusyTimeInterval(Day.WEDNESDAY, new TimeInterval(15, 16, IntervalColor.RED));
        var otherSchedule = new Schedule();
        otherSchedule.addBusyTimeInterval(Day.WEDNESDAY, new TimeInterval(15, 16, IntervalColor.RED));
        assertThat(schedule.equals(otherSchedule)).isTrue();
    }

    private static void addBusyTimeIntervals(Schedule schedule) {
        schedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(10, 11));
        schedule.addBusyTimeInterval(Day.FRIDAY, new TimeInterval(13, 14));
        schedule.addBusyTimeInterval(Day.SATURDAY, new TimeInterval(15, 16));
        schedule.addBusyTimeInterval(Day.SUNDAY, new TimeInterval(18, 19));
    }
}