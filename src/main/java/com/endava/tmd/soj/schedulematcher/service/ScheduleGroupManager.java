package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.model.ScheduleGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The {@code ScheduleGroupManager} is capable of creating new {@link ScheduleGroup} instances, adding members
 * to them and getting the combined schedule of all {@link Schedule} instances in a {@link ScheduleGroup}.
 * It identifies {@link ScheduleGroup} instances by a unique 6-character string.
 */
public class ScheduleGroupManager {
    private Map<String, ScheduleGroup> groups;

    public ScheduleGroupManager() {
        this.groups = new HashMap<>();
    }

    /**
     * Creates a new {@link ScheduleGroup} and returns its identifier
     * @param maxParticipants maximum number of participants allowed into the newly created {@link ScheduleGroup}
     * @return groupID of the newly created {@link ScheduleGroup}
     */
    public String createGroup(int maxParticipants) {
        String groupCode = GroupCodeGenerator.getGroupCode(6);

        while (groups.containsKey(groupCode)) {
            groupCode = GroupCodeGenerator.getGroupCode(6);
        }

        groups.put(groupCode, new ScheduleGroup(maxParticipants));

        return groupCode;
    }

    /**
     * Adds a new {@link Schedule} to the {@link ScheduleGroup} with the given group code
     * @param groupCode the group code of the {@link ScheduleGroup}
     * @param schedule the new {@link Schedule} that needs to be added
     */
    public void registerMemberSchedule(String groupCode, Schedule schedule) {

        if (!groups.containsKey(groupCode)) {
            throw new IllegalArgumentException("No group with given ID found");
        }

        groups.get(groupCode).addMemberSchedule(schedule);
    }

    /**
     * @param groupID the identifier of the {@link ScheduleGroup} for which the method needs to check if the max size has been reached
     * @return true if the max size has been reached for the {@link ScheduleGroup} with the given group ID
     */
    public boolean hasMaxSizeBeenReached(String groupID) {
        return groups.get(groupID).hasMaxSizeBeenReached();
    }

    /**
     * Returns a combined schedule of all schedules in the schedule group with the given identifier
     * @param groupID the group ID for the {@link ScheduleGroup} for which the method need to combine all schedules
     * @return a combined schedule of all schedules in the schedule group
     * @see ScheduleCombiner
     */
    Schedule getCombinedSchedule(String groupID) {
        return ScheduleCombiner.getCombinedSchedule(groups.get(groupID));
    }

    /**
     * @param groupID the ID of the {@link ScheduleGroup} that needs to be returned
     * @return the {@link ScheduleGroup} with the given group ID if it exists, otherwise an empty Optional
     */
    public Optional<ScheduleGroup> getGroup(String groupID) {
        if (!groups.containsKey(groupID)) {
            return Optional.empty();
        }
        return Optional.of(groups.get(groupID));
    }

}
