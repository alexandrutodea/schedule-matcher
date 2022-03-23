package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.exception.InvalidExcelFileException;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tests for the ExcelFileScheduleLoader class")
class ExcelFileScheduleLoaderTest {

    private final ScheduleLoader scheduleLoader = new ExcelFileScheduleLoader();

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

    @ParameterizedTest(name = "InvalidExcelFile exception should be thrown when trying to open {0}.xlsx file")
    @MethodSource(value = "com.endava.tmd.soj.schedulematcher.service.ExcelTestDataGenerator#getInvalidTestData")
    void appropriateExceptionsGetThrownForInvalidTestFiles(String fileName, String errorMessage) {
        assertThatThrownBy(() -> scheduleLoader.loadSchedule(new FileInputStream(Utils.buildFilePath(Directory.INVALID, fileName))))
                .isInstanceOf(InvalidExcelFileException.class)
                .hasMessage(errorMessage);
    }

}