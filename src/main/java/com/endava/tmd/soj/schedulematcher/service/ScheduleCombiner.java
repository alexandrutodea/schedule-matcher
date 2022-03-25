package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.Day;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.model.ScheduleGroup;
import com.endava.tmd.soj.schedulematcher.model.TimeInterval;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ScheduleCombiner} is a service that merges all the schedules in a {@code ScheduleGroup}
 * @see ScheduleGroup
 */
public class ScheduleCombiner {
    /**
     * Returns a merged schedule of all schedules in a {@link ScheduleGroup} and marks every busy time interval
     * with an {@link com.endava.tmd.soj.schedulematcher.model.IntervalColor} that indicates the people's overall
     * availability in the time interval
     * @param scheduleGroup the {@link ScheduleGroup} that we want to merge
     * @return a merged schedule of all schedules in the {@link ScheduleGroup}
     * @see com.endava.tmd.soj.schedulematcher.model.IntervalColor
     * @see ScheduleGroup
     */
    public static Schedule getCombinedSchedule(ScheduleGroup scheduleGroup) {

        /* We should have a function which shows us if there is a TimeInterval booked */
        /* There is one but is private. We should avoid throwing exceptions and provide a function which checks if
        the TimeInterval is present.
         */

        List<Schedule> scheduleList = scheduleGroup.getMemberSchedules();

        Schedule combinedSchedules = new Schedule();

        scheduleList.forEach(schedule -> {

            Optional<List<TimeInterval>> optionalTimeInterval = schedule.getBusyTimeIntervals(Day.MONDAY);

            /* If any schedule is present on MONDAY */
            if(optionalTimeInterval.isPresent()){

                /* For each time interval on MONDAY */
                optionalTimeInterval.get().forEach(timeInterval -> {
                    combinedSchedules.addBusyTimeInterval(Day.MONDAY, timeInterval);
                });
            }

            optionalTimeInterval = schedule.getBusyTimeIntervals(Day.TUESDAY);

            /* If any schedule is present on TUESDAY */
            if(optionalTimeInterval.isPresent()){

                /* For each time interval on TUESDAY */
                optionalTimeInterval.get().forEach(timeInterval -> {
                    combinedSchedules.addBusyTimeInterval(Day.TUESDAY, timeInterval);
                });
            }

            optionalTimeInterval = schedule.getBusyTimeIntervals(Day.WEDNESDAY);

            /* If any schedule is present on WEDNESDAY */
            if(optionalTimeInterval.isPresent()){

                /* For each time interval on WEDNESDAY */
                optionalTimeInterval.get().forEach(timeInterval -> {
                    combinedSchedules.addBusyTimeInterval(Day.WEDNESDAY, timeInterval);
                });
            }
            optionalTimeInterval = schedule.getBusyTimeIntervals(Day.THURSDAY);

            /* If any schedule is present on THURSDAY */
            if(optionalTimeInterval.isPresent()){

                /* For each time interval on THURSDAY */
                optionalTimeInterval.get().forEach(timeInterval -> {
                    combinedSchedules.addBusyTimeInterval(Day.THURSDAY, timeInterval);
                });
            }

            optionalTimeInterval = schedule.getBusyTimeIntervals(Day.FRIDAY);

            /* If any schedule is present on FRIDAY */
            if(optionalTimeInterval.isPresent()){

                /* For each time interval on FRIDAY */
                optionalTimeInterval.get().forEach(timeInterval -> {
                    combinedSchedules.addBusyTimeInterval(Day.FRIDAY, timeInterval);
                });
            }

            optionalTimeInterval = schedule.getBusyTimeIntervals(Day.SATURDAY);

            /* If any schedule is present on SATURDAY */
            if(optionalTimeInterval.isPresent()){

                /* For each time interval on SATURDAY */
                optionalTimeInterval.get().forEach(timeInterval -> {
                    combinedSchedules.addBusyTimeInterval(Day.SATURDAY, timeInterval);
                });
            }

            optionalTimeInterval = schedule.getBusyTimeIntervals(Day.SUNDAY);

            /* If any schedule is present on SUNDAY */
            if(optionalTimeInterval.isPresent()){

                /* For each time interval on SUNDAY */
                optionalTimeInterval.get().forEach(timeInterval -> {
                    combinedSchedules.addBusyTimeInterval(Day.SUNDAY, timeInterval);
                });
            }

        });

        return combinedSchedules;
    }
}
