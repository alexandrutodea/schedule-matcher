package com.endava.tmd.soj.schedulematcher.model;

import java.util.Objects;

/**
 * The {@code TimeInterval} class represents a period of time between a starting hour and an ending hour in 24-hour format.
 */
public class TimeInterval {

    private final int start;
    private final int end;
    private IntervalColor intervalColor;

    /**
     * @param start the starting hour for the time interval in 24-hour format
     * @param end the ending hour for the time interval in 24-hour format
     * @exception IllegalArgumentException if the starting hour is greater than or equal to the ending hour
     * @exception IllegalArgumentException if the starting or ending hour is negative
     */
    public TimeInterval(int start, int end) {

        if (start < 0 || end < 0) {
            throw new IllegalArgumentException("The starting and ending hours cannot be negative");
        }

        if (start >= end) {
            throw new IllegalArgumentException("The starting hour has to be strictly smaller than the ending hour");
        }

        this.start = start;
        this.end = end;
        this.intervalColor = IntervalColor.WHITE;
    }

    /**
     * Works just like {@link TimeInterval#TimeInterval(int, int)} except that a color representing the people's
     * availability in the given time interval can also be specified
     * @see TimeInterval#TimeInterval(int, int)
     */
    public TimeInterval(int start, int end, IntervalColor intervalColor) {
        this(start, end);
        this.intervalColor = intervalColor;
    }

    /**
     * @return the starting hour for the time interval in 24-hour format
     */
    public int getStart() {
        return start;
    }

    /**
     * @return the ending hour for the interval in 24-hour format
     */
    public int getEnd() {
        return end;
    }

    /**
     * @return color indicating the people's availability in the given time interval
     */
    public IntervalColor getIntervalColor() {
        return intervalColor;
    }

    /**
     * @param intervalColor color indicating the people's availability in the given time interval
     */
    public void setIntervalColor(IntervalColor intervalColor) {
        this.intervalColor = intervalColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeInterval that = (TimeInterval) o;
        return start == that.start && end == that.end && intervalColor == that.intervalColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, intervalColor);
    }

    @Override
    public String toString() {
        return "TimeInterval{" +
               "start=" + start +
               ", end=" + end +
               ", intervalColor=" + intervalColor +
               '}';
    }
}
