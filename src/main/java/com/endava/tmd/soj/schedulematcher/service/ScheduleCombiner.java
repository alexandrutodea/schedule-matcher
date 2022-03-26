package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.*;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code ScheduleCombiner} is a service that merges all the schedules in a {@code ScheduleGroup}
 * @see ScheduleGroup
 */
public class ScheduleCombiner {

    private ScheduleCombiner() {
        //it makes no sense to instantiate this class
    }

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

        var combined = new Schedule();

        for (Day day : Day.values()) {
            var intervals = scheduleGroup.getAllBusyIntervalsForDay(day);
            var coloredIntervals = getColoredIntervals(intervals);
            addColoredIntervals(day, combined, coloredIntervals);
        }

        return combined;

    }

    /**
     * adds all colored intervals for a given day in a given schedule
     * @param day the day for which the given colored time intervals need to be added
     * @param schedule the schedule in which the colored time intervals need to be added for a given day
     * @param coloredIntervals the colored intervals that need to be added for a given day in a given schedule
     */
    private static void addColoredIntervals(Day day, Schedule schedule, List<TimeInterval> coloredIntervals) {
        coloredIntervals.forEach(interval -> schedule
                .addBusyTimeInterval(day, interval));
    }

    /**
     * @param intervals a list of time intervals
     * @return a list of colored time intervals that results from combining the given time intervals
     * with the goal of reflecting the people's overall availability
     * @see IntervalColor
     */
    private static List<TimeInterval> getColoredIntervals(List<TimeInterval> intervals) {

        List<TimeInterval> coloredIntervals = new ArrayList<>();

        for (TimeInterval interval : intervals) {

            Optional<TimeInterval> alreadyExistingInterval = getAlreadyExistingInterval(coloredIntervals, interval);

            if (alreadyExistingInterval.isPresent()) {
                alreadyExistingInterval.get().setIntervalColor(IntervalColor.RED);
            } else {
                interval.setIntervalColor(IntervalColor.YELLOW);
                coloredIntervals.add(interval);
            }

        }

        return coloredIntervals;
    }

    /**
     * @param intervals a list of intervals
     * @param interval an interval
     * @return if an interval that has the same start time and end time as the given interval can be found in the given list of intervals,
     * then the matching interval from the given list of intervals is returned, otherwise the function returns an empty Optional
     */
    private static Optional<TimeInterval> getAlreadyExistingInterval(List<TimeInterval> intervals, TimeInterval interval) {

        for (TimeInterval existingInterval : intervals) {
            if (existingInterval.getStart() == interval.getStart() && existingInterval.getEnd() == interval.getEnd()) {
                return Optional.of(existingInterval);
            }
        }

        return Optional.empty();
    }

}
