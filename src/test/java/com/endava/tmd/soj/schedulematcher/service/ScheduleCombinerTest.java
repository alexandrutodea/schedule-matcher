package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tests for the Schedule Combiner service")
class ScheduleCombinerTest {

    private static final Schedule personBusyOnMondaySchedule = new Schedule();
    private static final Schedule personBusyOnTuesdaySchedule = new Schedule();
    private static final Schedule personBusyOnFridaySchedule = new Schedule();

    @BeforeAll
    static void beforeAll() {
        personBusyOnMondaySchedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16));
        personBusyOnTuesdaySchedule.addBusyTimeInterval(Day.TUESDAY, new TimeInterval(17, 18));
        personBusyOnFridaySchedule.addBusyTimeInterval(Day.FRIDAY, new TimeInterval(12, 13));
    }

    @Test
    @DisplayName("An exception gets throw if the given ScheduleGroup has not reached its maximum sized")
    void exceptionGetsThrownIfScheduleGroupHasNotReachedMaxSize() {
        var scheduleGroup = new ScheduleGroup(3);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        scheduleGroup.addMemberSchedule(personBusyOnTuesdaySchedule);
        assertThatThrownBy(() -> ScheduleCombiner.getCombinedSchedule(scheduleGroup))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The schedule group has not reached its maximum size");
    }

    @Test
    @DisplayName("combineSchedules returns the expected result if two people are busy in different time intervals")
    void twoPeopleBusyInDifferentTimeIntervals() {
        var scheduleGroup = new ScheduleGroup(2);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        scheduleGroup.addMemberSchedule(personBusyOnTuesdaySchedule);
        var expected = new Schedule();
        expected.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16, IntervalColor.YELLOW));
        expected.addBusyTimeInterval(Day.TUESDAY, new TimeInterval(17, 18, IntervalColor.YELLOW));
        assertResultedSchedule(scheduleGroup, expected);
    }

    @Test
    @DisplayName("combineSchedules returns the expected result if multiple people are busy in different time intervals")
    void multiplePeopleBusyInDifferentTimeIntervals() {
        var scheduleGroup = new ScheduleGroup(3);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        scheduleGroup.addMemberSchedule(personBusyOnTuesdaySchedule);
        scheduleGroup.addMemberSchedule(personBusyOnFridaySchedule);
        var expected = new Schedule();
        expected.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16, IntervalColor.YELLOW));
        expected.addBusyTimeInterval(Day.TUESDAY, new TimeInterval(17, 18, IntervalColor.YELLOW));
        expected.addBusyTimeInterval(Day.FRIDAY, new TimeInterval(12, 13, IntervalColor.YELLOW));
        assertResultedSchedule(scheduleGroup, expected);
    }

    @Test
    @DisplayName("combineSchedules returns the expected result if multiple people are busy in the same time interval and one person is busy in a different time interval")
    void oneIntervalTwoPeopleOneIntervalOnePerson() {
        var scheduleGroup = new ScheduleGroup(3);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        scheduleGroup.addMemberSchedule(personBusyOnTuesdaySchedule);
        var expected = new Schedule();
        expected.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16, IntervalColor.RED));
        expected.addBusyTimeInterval(Day.TUESDAY, new TimeInterval(17, 18, IntervalColor.YELLOW));
        assertResultedSchedule(scheduleGroup, expected);
    }

    @Test
    @DisplayName("combineSchedules returns the expected result if two people are busy in the same time interval")
    void twoPeopleAreBusyInTheSameTimeInterval() {
        var scheduleGroup = new ScheduleGroup(2);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        var expected = new Schedule();
        expected.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16, IntervalColor.RED));
        assertResultedSchedule(scheduleGroup, expected);
    }

    @Test
    @DisplayName("combineSchedules returns the expected result if multiple people are busy in the same time interval")
    void multiplePeopleBusyInTheSameTimeInterval() {
        var scheduleGroup = new ScheduleGroup(5);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        scheduleGroup.addMemberSchedule(personBusyOnMondaySchedule);
        var expected = new Schedule();
        expected.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16, IntervalColor.RED));
        assertResultedSchedule(scheduleGroup, expected);
    }

    public static void assertResultedSchedule(ScheduleGroup scheduleGroup, Schedule schedule) {
        assertThat(ScheduleCombiner
                .getCombinedSchedule(scheduleGroup))
                .isEqualTo(schedule);
    }

}