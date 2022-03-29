package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.*;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ScheduleCombiner} is a service that merges all the schedules in a {@code ScheduleGroup}
 *
 * @see ScheduleGroup
 */
public class ScheduleCombiner {
    /**
     * Returns a merged schedule of all schedules in a {@link ScheduleGroup} and marks every busy time interval
     * with an {@link com.endava.tmd.soj.schedulematcher.model.IntervalColor} that indicates the people's overall
     * availability in the time interval
     *
     * @param scheduleGroup the {@link ScheduleGroup} that we want to merge
     * @return a merged schedule of all schedules in the {@link ScheduleGroup}
     * @see com.endava.tmd.soj.schedulematcher.model.IntervalColor
     * @see ScheduleGroup
     */
    public static Schedule getCombinedSchedule(ScheduleGroup scheduleGroup) {

        List<Schedule> scheduleList = scheduleGroup.getMemberSchedules();

        Schedule combinedSchedules = new Schedule();

        scheduleList.forEach(schedule -> {

            for (Day day : Day.values()) {
                addToCombineSchedule(schedule, day, combinedSchedules);
            }
        });

        return combinedSchedules;
    }

    /**
    * @param scheduleToBeAdded
    */
    private static void addToCombineSchedule(Schedule scheduleToBeAdded, Day day, Schedule combinedSchedules){

        final Optional<List<TimeInterval>> optionalTimeIntervalMonday = scheduleToBeAdded.getBusyTimeIntervals(day);

        /* If any schedule is present on MONDAY */
        if (optionalTimeIntervalMonday.isPresent()) {

            /* For each time interval on MONDAY */
            optionalTimeIntervalMonday.get().forEach(timeInterval -> {

                if (!(combinedSchedules.doTimeIntervalExists(day, timeInterval))) {
                    combinedSchedules.addBusyTimeInterval(day, timeInterval);
                    timeInterval.setIntervalColor(IntervalColor.YELLOW);
                }else{
                    timeInterval.setIntervalColor(IntervalColor.RED);
                }
            });
        }
    }
}
