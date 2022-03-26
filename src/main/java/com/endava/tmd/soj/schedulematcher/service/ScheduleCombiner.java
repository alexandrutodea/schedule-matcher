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

        /* We should have a function which shows us if there is a TimeInterval booked */
        /* There is one but is private. We should avoid throwing exceptions and provide a function which checks if
        the TimeInterval is present.
         */

        List<Schedule> scheduleList = scheduleGroup.getMemberSchedules();

        Schedule combinedSchedules = new Schedule();

        scheduleList.forEach(schedule -> {

            final Optional<List<TimeInterval>> optionalTimeIntervalMonday = schedule.getBusyTimeIntervals(Day.MONDAY);

            /* If any schedule is present on MONDAY */
            if (optionalTimeIntervalMonday.isPresent()) {

                /* For each time interval on MONDAY */
                optionalTimeIntervalMonday.get().forEach(timeInterval -> {

                    if (!(combinedSchedules.isThereOverlapping(optionalTimeIntervalMonday.get(), timeInterval))) {
                        combinedSchedules.addBusyTimeInterval(Day.MONDAY, timeInterval);
                        timeInterval.setIntervalColor(IntervalColor.YELLOW);
                    }else{
                        timeInterval.setIntervalColor(IntervalColor.RED);
                    }
                });
            }

            final Optional<List<TimeInterval>> optionalTimeIntervalTuesday = schedule.getBusyTimeIntervals(Day.TUESDAY);

            /* If any schedule is present on TUESDAY */
            if (optionalTimeIntervalTuesday.isPresent()) {

                /* For each time interval on TUESDAY */
                optionalTimeIntervalTuesday.get().forEach(timeInterval -> {
                    if (!(combinedSchedules.isThereOverlapping(optionalTimeIntervalTuesday.get(), timeInterval))) {
                        combinedSchedules.addBusyTimeInterval(Day.TUESDAY, timeInterval);
                        timeInterval.setIntervalColor(IntervalColor.YELLOW);
                    }else{
                        timeInterval.setIntervalColor(IntervalColor.RED);
                    }
                });
            }

            final Optional<List<TimeInterval>> optionalTimeIntervalWednesday = schedule.getBusyTimeIntervals(Day.WEDNESDAY);

            /* If any schedule is present on WEDNESDAY */
            if (optionalTimeIntervalWednesday.isPresent()) {

                /* For each time interval on WEDNESDAY */
                optionalTimeIntervalWednesday.get().forEach(timeInterval -> {
                    if (!(combinedSchedules.isThereOverlapping(optionalTimeIntervalWednesday.get(), timeInterval))) {
                        combinedSchedules.addBusyTimeInterval(Day.WEDNESDAY, timeInterval);
                        timeInterval.setIntervalColor(IntervalColor.YELLOW);
                    }else{
                        timeInterval.setIntervalColor(IntervalColor.RED);
                    }
                });
            }

            final Optional<List<TimeInterval>> optionalTimeIntervalThursday = schedule.getBusyTimeIntervals(Day.THURSDAY);

            /* If any schedule is present on THURSDAY */
            if (optionalTimeIntervalThursday.isPresent()) {

                /* For each time interval on THURSDAY */
                optionalTimeIntervalThursday.get().forEach(timeInterval -> {
                    if (!(combinedSchedules.isThereOverlapping(optionalTimeIntervalThursday.get(), timeInterval))) {
                        combinedSchedules.addBusyTimeInterval(Day.THURSDAY, timeInterval);
                        timeInterval.setIntervalColor(IntervalColor.YELLOW);
                    }else{
                        timeInterval.setIntervalColor(IntervalColor.RED);
                    }
                });
            }

            final Optional<List<TimeInterval>> optionalTimeIntervalFriday = schedule.getBusyTimeIntervals(Day.FRIDAY);

            /* If any schedule is present on FRIDAY */
            if (optionalTimeIntervalFriday.isPresent()) {

                /* For each time interval on FRIDAY */
                optionalTimeIntervalFriday.get().forEach(timeInterval -> {
                    if (!(combinedSchedules.isThereOverlapping(optionalTimeIntervalFriday.get(), timeInterval))) {
                        combinedSchedules.addBusyTimeInterval(Day.FRIDAY, timeInterval);
                        timeInterval.setIntervalColor(IntervalColor.YELLOW);
                    }else{
                        timeInterval.setIntervalColor(IntervalColor.RED);
                    }
                });
            }

            final Optional<List<TimeInterval>> optionalTimeIntervalSaturday = schedule.getBusyTimeIntervals(Day.SATURDAY);

            /* If any schedule is present on SATURDAY */
            if (optionalTimeIntervalSaturday.isPresent()) {

                /* For each time interval on SATURDAY */
                optionalTimeIntervalSaturday.get().forEach(timeInterval -> {
                    if (!(combinedSchedules.isThereOverlapping(optionalTimeIntervalSaturday.get(), timeInterval))) {
                        combinedSchedules.addBusyTimeInterval(Day.SATURDAY, timeInterval);
                        timeInterval.setIntervalColor(IntervalColor.YELLOW);
                    }else{
                        timeInterval.setIntervalColor(IntervalColor.RED);
                    }
                });
            }

            final Optional<List<TimeInterval>> optionalTimeIntervalSunday = schedule.getBusyTimeIntervals(Day.SUNDAY);

            /* If any schedule is present on SUNDAY */
            if (optionalTimeIntervalSunday.isPresent()) {

                /* For each time interval on SUNDAY */
                optionalTimeIntervalSunday.get().forEach(timeInterval -> {
                    if (!(combinedSchedules.isThereOverlapping(optionalTimeIntervalSunday.get(), timeInterval))) {
                        combinedSchedules.addBusyTimeInterval(Day.SUNDAY, timeInterval);
                        timeInterval.setIntervalColor(IntervalColor.YELLOW);
                    }else{
                        timeInterval.setIntervalColor(IntervalColor.RED);
                    }
                });
            }

        });

        return combinedSchedules;
    }
}
