package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.exception.InvalidExcelFileException;
import com.endava.tmd.soj.schedulematcher.exception.UnableToReadExcelFileException;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tests for the ExcelFileScheduleLoader class")
class ExcelFileScheduleLoaderTest {

    private final ScheduleLoader scheduleLoader = new ExcelFileScheduleLoader();

    @Test
    @DisplayName("Loader should throw an exception when the input file is in an invalid format")
    void shouldThrowExceptionWhenValidIsInInvalidFormat() {
        assertThatThrownBy(() -> scheduleLoader.loadSchedule(new FileInputStream("pom.xml")))
                .isInstanceOf(UnableToReadExcelFileException.class)
                .hasMessage("Cannot construct instance of `" + Schedule.class.getName() + "`");
    }

    @ParameterizedTest(name = "Loader works properly for {0}.xlsx file")
    @MethodSource(value = "com.endava.tmd.soj.schedulematcher.service.ExcelTestDataGenerator#getValidNonColorFilesTestData")
    void validNonColorTestFilesGetLoadedProperly(String fileName, Schedule expectedSchedule) throws FileNotFoundException {
        assertThat(scheduleLoader.loadSchedule(new FileInputStream(Utils.buildFilePath(Directory.VALID_NON_COLOR, fileName))))
                .isEqualTo(expectedSchedule);
    }

    @ParameterizedTest(name = "Loader works properly {0}.xlsx file")
    @MethodSource(value = "com.endava.tmd.soj.schedulematcher.service.ExcelTestDataGenerator#getValidColorFilesTestData")
    void validColorTestFilesGetLoadedProperly(String fileName, Schedule expectedSchedule) throws FileNotFoundException {
        assertThat(scheduleLoader.loadSchedule(new FileInputStream(Utils.buildFilePath(Directory.VALID_COLOR, fileName))))
                .isEqualTo(expectedSchedule);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource(value = "com.endava.tmd.soj.schedulematcher.service.ExcelTestDataGenerator#getInvalidTestData")
    void appropriateExceptionsGetThrownForInvalidTestFiles(String fileName) {
        assertThatThrownBy(() -> scheduleLoader.loadSchedule(new FileInputStream(Utils.buildFilePath(Directory.INVALID, fileName))))
                .isInstanceOf(InvalidExcelFileException.class)
                .hasMessage("Invalid Excel schedule file");
    }

}