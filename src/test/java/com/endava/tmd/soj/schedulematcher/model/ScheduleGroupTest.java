package com.endava.tmd.soj.schedulematcher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tests for the Schedule Group class")
class ScheduleGroupTest {

    @Test
    @DisplayName("Schedule group gets instantiated as expected")
    void initializationWorksAsExpected() {
        var scheduleGroup = new ScheduleGroup(5);
        assertThat(scheduleGroup.getMaxSize())
                .isEqualTo(5);
        assertThat(scheduleGroup.getMemberSchedules()).isNotNull();
    }

    @Test
    @DisplayName("Adding schedules to the schedule group works as expected")
    void addingSchedulesWorksAsExpected() {
        var schedule = new Schedule();
        var scheduleGroup = new ScheduleGroup(3);
        scheduleGroup.addMemberSchedule(schedule);
        assertThat(scheduleGroup
                .getMemberSchedules())
                .containsExactly(schedule);
    }

    @Test
    @DisplayName("An exception gets thrown if an attempt is made to go over the maximum group size")
    void attemptToGoOverMaximumGroupSize() {
        var scheduleGroup = new ScheduleGroup(2);
        scheduleGroup.addMemberSchedule(new Schedule());
        scheduleGroup.addMemberSchedule(new Schedule());
        assertThatThrownBy(() -> scheduleGroup.addMemberSchedule(new Schedule()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The maximum number of participants has been reached for this group");
    }

}