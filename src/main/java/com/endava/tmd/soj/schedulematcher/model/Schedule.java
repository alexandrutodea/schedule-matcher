package com.endava.tmd.soj.schedulematcher.model;

import java.util.*;

/**
 * The {@code Schedule} class represents a person's weekly timetable
 * For each day of the week, it keeps track of the time intervals in which the person is busy
 */

public class Schedule {

    private final Map<Day, List<TimeInterval>> busyTimeIntervals;

    /**
     * initializes an empty Schedule object with no busy time intervals.
     */
    public Schedule() {
        this.busyTimeIntervals = new HashMap<>();
    }

    /**
     * Marks a time interval as busy for the given day
     *
     * @param day          the day of the week for which a time interval should to be marked as busy
     * @param timeInterval the time interval that should be marked as busy
     * @throws IllegalArgumentException if the time interval has already been marked as busy for the given day
     * @throws IllegalArgumentException if the time interval overlaps with another time interval that has been marked as busy for the given day
     */
    public void addBusyTimeInterval(Day day, TimeInterval timeInterval) {

        var markedTimeIntervals = busyTimeIntervals.get(day);

        if (markedTimeIntervals == null) {
            busyTimeIntervals.put(day, new ArrayList<>());
            busyTimeIntervals.get(day).add(timeInterval);
        } else if (markedTimeIntervals.size() == 0) {
            markedTimeIntervals.add(timeInterval);
        } else if (markedTimeIntervals.contains(timeInterval)) {
            throw new IllegalArgumentException("This time interval has already been marked as busy for the given day");
        } else if (isThereOverlapping(markedTimeIntervals, timeInterval)) {
            throw new IllegalArgumentException("This time interval overlaps with another time interval that has been marked as busy for the given day");
        } else {
            markedTimeIntervals.add(timeInterval);
        }

    }

    /**
     * @param day the day of the week for which the busy time intervals should be returned
     * @return the busy time intervals in the given day
     */
    public Optional<List<TimeInterval>> getBusyTimeIntervals(Day day) {
        return Optional.ofNullable(busyTimeIntervals.get(day));
    }

    /**
     * @param markedIntervals the time intervals that have been marked as busy for a given day
     * @param timeInterval the time interval that we want to check for overlapping
     * @return true if the time interval overlaps with any of the marked time intervals
     */
    private boolean isThereOverlapping(List<TimeInterval> markedIntervals, TimeInterval timeInterval) {
        return markedIntervals
                .stream()
                .anyMatch(markedInterval -> isStartHourInMarkedInterval(markedInterval, timeInterval)
                || isStartHourInMarkedInterval(timeInterval, markedInterval));
    }

    /**
     * @param markedInterval a time interval that has been marked a busy
     * @param timeInterval a time interval that we want to check for overlapping
     * @return true if the start hour of the time interval is in the marked time interval
     */
    private boolean isStartHourInMarkedInterval(TimeInterval markedInterval, TimeInterval timeInterval) {
        return timeInterval.getStart() >= markedInterval.getStart()
               && timeInterval.getStart() < markedInterval.getEnd();
    }

    /**
     * @param day the day that needs to be checked for a busy interval
     * @param interval the interval that we want to check whether it is busy for the given day or not
     * @return true if the time interval has been marked as busy for the given day
     */
    public boolean hasIntervalBeenMarkedAsBusy(Day day, TimeInterval interval) {
        var busyIntervals = busyTimeIntervals.get(day);
        if (busyIntervals != null) {
            return busyIntervals.contains(interval);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        if (this.busyTimeIntervals.entrySet().size() != schedule.busyTimeIntervals.entrySet().size()) return false;

        for (Day day : this.busyTimeIntervals.keySet()) {

            if (!schedule.busyTimeIntervals.containsKey(day)) {
                return false;
            }

            if (!this.busyTimeIntervals.get(day).equals(schedule.busyTimeIntervals.get(day))) {
                return false;
            }

        }

        return true;
    }

    @Override
    public String toString() {
        return "Schedule{" +
               "busyTimeIntervals=" + busyTimeIntervals +
               '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(busyTimeIntervals);
    }
}
