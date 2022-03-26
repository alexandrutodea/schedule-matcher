package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.Day;
import com.endava.tmd.soj.schedulematcher.model.IntervalColor;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.model.TimeInterval;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ExcelTestDataGenerator {

    private ExcelTestDataGenerator() {
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
                arguments(named("A schedule cannot contain both an X and a color", "colorAndXSchedule")),
                arguments(named("A schedule file must contain at least one busy interval", "emptySchedule")),
                arguments(named("A cell may only contain a lowercase x", "invalidCellContentSchedule")),
                arguments(named("A cell may only be colored in either white, red, green or yellow", "invalidColorSchedule")),
                arguments(named("An invalid day name has been detected", "invalidDaySchedule")),
                arguments(named("An invalid interval has been detected (only 8:00-19:00 accepted)", "invalidIntervalSchedule")),
                arguments(named("The file does not contain all weekdays", "missingDaySchedule")),
                arguments(named("The file does not contain all 8:00-19:00 intervals", "missingIntervalSchedule"))
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