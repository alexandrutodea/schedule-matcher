package com.endava.tmd.soj.schedulematcher.service;

import org.junit.jupiter.params.provider.Arguments;

import java.io.File;
import java.util.stream.Stream;

class Utils {

    private Utils() {
        //it makes no sense to instantiate this class
    }

    private static final String TEST_FILES_EXT = ".xlsx";

    public static String buildFilePath(Directory directory, String fileName) {
        return directory.getDirectoryPath() + "/" + fileName + TEST_FILES_EXT;
    }

    public static String generateTestDisplayName(String fileName) {
        return String.join(" ", fileName.split("[A-Z]")).toLowerCase();
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

}
