package com.endava.tmd.soj.schedulematcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tests for the Schedule Group class")
class ScheduleGroupTest {

    private ScheduleGroup scheduleGroup;

    @BeforeEach
    void setUp() {
        scheduleGroup = new ScheduleGroup(3);
    }

    @Test
    @DisplayName("Schedule group gets instantiated as expected")
    void initializationWorksAsExpected() {
        assertThat(scheduleGroup.getMaxSize())
                .isEqualTo(3);
        assertThat(scheduleGroup.getMemberSchedules()).isNotNull();
    }

    @Test
    @DisplayName("Adding schedules to the schedule group works as expected")
    void addingSchedulesWorksAsExpected() {
        var schedule = new Schedule();
        scheduleGroup.addMemberSchedule(schedule);
        assertThat(scheduleGroup
                .getMemberSchedules())
                .containsExactly(schedule);
    }

    @Test
    @DisplayName("An exception gets thrown if an attempt is made to go over the maximum group size")
    void attemptToGoOverMaximumGroupSize() {
        scheduleGroup.addMemberSchedule(new Schedule());
        scheduleGroup.addMemberSchedule(new Schedule());
        scheduleGroup.addMemberSchedule(new Schedule());
        assertThatThrownBy(() -> scheduleGroup.addMemberSchedule(new Schedule()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The maximum number of participants has been reached for this group");
    }

    @Test
    @DisplayName("Method asking if the max size has been reached returns false if the max size has not been reached")
    void hasMaxSizeBeenReachedReturnsFalseIfTheCase() {
        scheduleGroup.addMemberSchedule(new Schedule());
        assertThat(scheduleGroup.hasMaxSizeBeenReached()).isFalse();
    }

    @Test
    @DisplayName("Method asking if the max size has been reached returns true if the max size has been reached")
    void hasMaxSizeBeenReachedReturnsTrueIfTheCase() {
        scheduleGroup.addMemberSchedule(new Schedule());
        scheduleGroup.addMemberSchedule(new Schedule());
        scheduleGroup.addMemberSchedule(new Schedule());
        assertThat(scheduleGroup.hasMaxSizeBeenReached()).isTrue();
    }

}