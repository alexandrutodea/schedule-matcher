package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.Day;
import com.endava.tmd.soj.schedulematcher.model.IntervalColor;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.model.TimeInterval;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TestDataGenerator {

    private TestDataGenerator() {
        //it makes no sense to instantiate this class
    }

    public static Stream<Arguments> getValidNonColorFilesTestData() {
        return Stream.of(
                Arguments.of("multipleBusyIntervalsOnMultipleDifferentDaysSchedule", buildSchedule(

                        Map.of(Day.MONDAY,
                                List.of(new TimeInterval(8, 9), new TimeInterval(9, 10), new TimeInterval(10, 11),
                                        new TimeInterval(16, 17), new TimeInterval(17, 18), new TimeInterval(18, 19)),
                                Day.TUESDAY,
                                List.of(new TimeInterval(10, 11), new TimeInterval(11, 12), new TimeInterval(12, 13),
                                        new TimeInterval(14, 15), new TimeInterval(15, 16), new TimeInterval(16, 17)
                                ),
                                Day.WEDNESDAY, List.of(
                                        new TimeInterval(10, 11), new TimeInterval(11, 12), new TimeInterval(12, 13),
                                        new TimeInterval(14, 15), new TimeInterval(15, 16), new TimeInterval(16, 17)
                                ),
                                Day.THURSDAY, List.of(
                                        new TimeInterval(10, 11), new TimeInterval(11, 12), new TimeInterval(12, 13),
                                        new TimeInterval(14, 15), new TimeInterval(15, 16), new TimeInterval(16, 17)
                                ), Day.FRIDAY, List.of(
                                        new TimeInterval(8, 9), new TimeInterval(9, 10), new TimeInterval(10, 11),
                                        new TimeInterval(16, 17), new TimeInterval(17, 18), new TimeInterval(18, 19)
                                ), Day.SATURDAY, List.of(
                                        new TimeInterval(8, 9), new TimeInterval(9, 10), new TimeInterval(10, 11),
                                        new TimeInterval(11, 12), new TimeInterval(12, 13), new TimeInterval(13, 14),
                                        new TimeInterval(14, 15), new TimeInterval(15, 16), new TimeInterval(16, 17),
                                        new TimeInterval(17, 18), new TimeInterval(18, 19)
                                ), Day.SUNDAY, List.of(
                                        new TimeInterval(8, 9), new TimeInterval(9, 10), new TimeInterval(10, 11),
                                        new TimeInterval(11, 12), new TimeInterval(12, 13), new TimeInterval(13, 14),
                                        new TimeInterval(14, 15), new TimeInterval(15, 16), new TimeInterval(16, 17),
                                        new TimeInterval(17, 18), new TimeInterval(18, 19))))),

                Arguments.of("oneBusyIntervalEveryDaySchedule", buildSchedule(
                        Map.of(Day.MONDAY, List.of(new TimeInterval(8, 9)),
                                Day.TUESDAY, List.of(new TimeInterval(9, 10)),
                                Day.WEDNESDAY, List.of(new TimeInterval(10, 11)),
                                Day.THURSDAY, List.of(new TimeInterval(11, 12)),
                                Day.FRIDAY, List.of(new TimeInterval(12, 13)),
                                Day.SATURDAY, List.of(new TimeInterval(13, 14)),
                                Day.SUNDAY, List.of(new TimeInterval(14, 15))))),

                Arguments.of("oneBusyIntervalOnDifferentDaysSchedule", buildSchedule(
                        Map.of(Day.MONDAY, List.of(new TimeInterval(8, 9)),
                                Day.TUESDAY, List.of(new TimeInterval(9, 10))))),

                Arguments.of("oneBusyIntervalSchedule", buildSchedule(
                        Map.of(Day.MONDAY, List.of(new TimeInterval(8, 9))))),

                Arguments.of("twoBusyIntervalsEveryDaySchedule", buildSchedule(
                        Map.of(Day.MONDAY, List.of(new TimeInterval(8, 9), new TimeInterval(18, 19)),
                                Day.TUESDAY, List.of(new TimeInterval(9, 10), new TimeInterval(17, 18)),
                                Day.WEDNESDAY, List.of(new TimeInterval(10, 11), new TimeInterval(16, 17)),
                                Day.THURSDAY, List.of(new TimeInterval(11, 12), new TimeInterval(15, 16)),
                                Day.FRIDAY, List.of(new TimeInterval(12, 13), new TimeInterval(14, 15)),
                                Day.SATURDAY, List.of(new TimeInterval(11, 12), new TimeInterval(15, 16)),
                                Day.SUNDAY, List.of(new TimeInterval(10, 11), new TimeInterval(16, 17))))),

                Arguments.of("twoBusyIntervalsOnDifferentDaysSchedule", buildSchedule(
                        Map.of(Day.MONDAY, List.of(new TimeInterval(8, 9), new TimeInterval(9, 10)),
                                Day.WEDNESDAY, List.of(new TimeInterval(8, 9), new TimeInterval(9, 10))))),

                Arguments.of("twoBusyIntervalsOnTheSameDay", buildSchedule(
                        Map.of(Day.FRIDAY, List.of(new TimeInterval(8, 9), new TimeInterval(9, 10))))));
    }

    public static Stream<Arguments> getValidColorFilesTestData() {
        return Stream.of(
                Arguments.of("allGreenTimeSlotsSchedule", buildSchedule(Map.of())),

                Arguments.of("multipleRedAndYellowIntervalsSchedule", buildSchedule(
                        Map.of(Day.MONDAY,
                                List.of(new TimeInterval(14, 15, IntervalColor.YELLOW),
                                        new TimeInterval(17, 18, IntervalColor.RED)),
                                Day.TUESDAY, List.of(new TimeInterval(11, 12, IntervalColor.YELLOW)),
                                Day.WEDNESDAY, List.of(new TimeInterval(13, 14, IntervalColor.YELLOW),
                                        new TimeInterval(14, 15, IntervalColor.YELLOW)),
                                Day.FRIDAY, List.of(new TimeInterval(11, 12, IntervalColor.YELLOW),
                                        new TimeInterval(15, 16, IntervalColor.RED),
                                        new TimeInterval(17, 18, IntervalColor.RED)),
                                Day.SATURDAY, List.of(new TimeInterval(17, 18, IntervalColor.RED)),
                                Day.SUNDAY, List.of(new TimeInterval(12, 13, IntervalColor.RED))))),

                Arguments.of("multipleRedIntervalsSchedule", buildSchedule(
                        Map.of(Day.FRIDAY, List.of(new TimeInterval(17, 18)),
                                Day.SATURDAY, List.of(new TimeInterval(17, 18))))),

                Arguments.of("multipleYellowIntervalsSchedule", buildSchedule(
                        Map.of(Day.FRIDAY, List.of(new TimeInterval(17, 18, IntervalColor.YELLOW)),
                                Day.SATURDAY, List.of(new TimeInterval(17, 18, IntervalColor.YELLOW))))),

                Arguments.of("oneRedIntervalSchedule", buildSchedule(Map.of(
                        Day.FRIDAY, List.of(new TimeInterval(17, 18, IntervalColor.RED))))),

                Arguments.of("oneYellowIntervalSchedule", buildSchedule(Map.of(
                        Day.FRIDAY, List.of(new TimeInterval(17, 18, IntervalColor.YELLOW))))));
    }

    public static Stream<Arguments> getInvalidTestData() {
        return Stream.of(
                Arguments.of("colorAndXSchedule", "A cell cannot be both marked with an X and a color"),
                Arguments.of("emptySchedule", "A schedule file must contain at least one busy interval"),
                Arguments.of("invalidCellContentSchedule", "A cell may only contain a lowercase x"),
                Arguments.of("invalidColorSchedule", "A cell may only be colored in either white, red, green or yellow"),
                Arguments.of("invalidDaySchedule", "An invalid day name has been detected"),
                Arguments.of("invalidIntervalSchedule", "An invalid interval has been detected (only 8:00-19:00 accepted)"),
                Arguments.of("missingDaySchedule", "The file does not contain all weekdays"),
                Arguments.of("missingIntervalSchedule", "The file does not contain all 8:00-19:00 intervals")
        );
    }

    private static Schedule buildSchedule(Map<Day, List<TimeInterval>> busyIntervals) {
        var schedule = new Schedule();

        for (var entry : busyIntervals.entrySet()) {
            for (var timeInterval : entry.getValue()) {
                var day = entry.getKey();
                schedule.addBusyTimeInterval(day, timeInterval);
            }
        }

        return schedule;
    }

}