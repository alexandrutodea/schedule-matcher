package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;

@DisplayName("Tests for the ScheduleManager class")
class ScheduleGroupManagerTest {

    private final ScheduleGroupManager scheduleGroupManager = new ScheduleGroupManager();

    @Test
    @DisplayName("Schedules get properly registered in group")
    void schedulesGetAddedToGroupManager() {
        var groupID = scheduleGroupManager.createGroup(2);
        scheduleGroupManager.registerMemberSchedule(groupID, new Schedule());
        scheduleGroupManager.registerMemberSchedule(groupID, new Schedule());
        assertThat(scheduleGroupManager.hasMaxSizeBeenReached(groupID)).isTrue();
    }

    @Test
    @DisplayName("Method that checks whether the max size has been reached returns false if max size has not been reached")
    void hasMaximumSizeReturnsFalseIfTheCase() {
        var groupID = scheduleGroupManager.createGroup(3);
        scheduleGroupManager.registerMemberSchedule(groupID, new Schedule());
        scheduleGroupManager.registerMemberSchedule(groupID, new Schedule());
        assertThat(scheduleGroupManager.hasMaxSizeBeenReached(groupID)).isFalse();
    }

    @Test
    @DisplayName("Method that checks whether the max size has been reached returns true if max size has been reached")
    void hasMaximumSizeReturnsTrueIfTheCase() {
        var groupID = scheduleGroupManager.createGroup(5);
        for (int i = 0; i < 5; i++) {
            scheduleGroupManager.registerMemberSchedule(groupID, new Schedule());
        }
        assertThat(scheduleGroupManager.hasMaxSizeBeenReached(groupID)).isTrue();
    }

    @Test
    @DisplayName("An exception gets thrown if there is an attempt to create a group with less than two members")
    void exceptionGetsThrownIfGroupWithOneMemberIsCreated() {
        assertThatThrownBy(() -> scheduleGroupManager.createGroup(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A group must contain at least two members");
    }

    @Test
    @DisplayName("Correct combined schedule gets returned")
    void correctCombinedScheduleGetsReturned() {
        //arrange
        var groupID = scheduleGroupManager.createGroup(2);

        Schedule busyOnFridaySchedule = new Schedule();
        busyOnFridaySchedule.addBusyTimeInterval(Day.FRIDAY, new TimeInterval(15, 16));
        scheduleGroupManager.registerMemberSchedule(groupID, busyOnFridaySchedule);
        scheduleGroupManager.registerMemberSchedule(groupID, busyOnFridaySchedule);

        Optional<ScheduleGroup> scheduleGroup = scheduleGroupManager.getGroup(groupID);

        var expectedSchedule = new Schedule();
        expectedSchedule.addBusyTimeInterval(Day.FRIDAY, new TimeInterval(15, 16, IntervalColor.RED));

        if (scheduleGroup.isPresent()) {

            try (MockedStatic<ScheduleCombiner> combinerMock = mockStatic(ScheduleCombiner.class)) {
                combinerMock.when(() -> ScheduleCombiner
                        .getCombinedSchedule(scheduleGroup.get()))
                        .thenReturn(expectedSchedule);

                //act
                var combinedSchedule = scheduleGroupManager.getCombinedSchedule(groupID);

                //assert
                assertThat(combinedSchedule).isEqualTo(expectedSchedule);
            }

        }
    }

}