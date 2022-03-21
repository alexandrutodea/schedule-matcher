package com.endava.tmd.soj.schedulematcher.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ScheduleGroup} class represents a collection of group member schedules
 */
public class ScheduleGroup {
    private final List<Schedule> memberSchedules;
    private final int maxSize;

    /**
     * Initializes an empty schedule group and sets a maximum number of members for it
     * @param maxSize the maximum number of members allowed into the group
     * @exception IllegalArgumentException if there is attempt to create a group with less than two members
     */
    public ScheduleGroup(int maxSize) {

        if (maxSize < 2) {
            throw new IllegalArgumentException("A group must contain at least two members");
        }

        this.maxSize = maxSize;
        this.memberSchedules = new ArrayList<>();
    }

    /**
     * Adds a schedule that belongs to one member of the group
     * @param schedule the schedule that should be added to the schedule group
     */
    public void addMemberSchedule(Schedule schedule) {

        if (memberSchedules.size() == maxSize) {
            throw new IllegalStateException("The maximum number of participants has been reached for this group");
        }

        memberSchedules.add(schedule);
    }

    /**
     * @return the schedules for all group members
     */
    public List<Schedule> getMemberSchedules() {
        return memberSchedules;
    }

    /**
     * @return maximum number of members allowed into the group
     */
    public int getMaxSize() {
        return maxSize;
    }
}
