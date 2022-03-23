package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for ExcelFileScheduleWriter class")
class ExcelFileScheduleWriterTest {

    private final ScheduleWriter scheduleWriter = new ExcelFileScheduleWriter();
    private final ScheduleLoader scheduleLoader = new ExcelFileScheduleLoader();

    @ParameterizedTest(name = "Writer works properly for {0}.xlsx file")
    @MethodSource(value = "com.endava.tmd.soj.schedulematcher.service.ExcelTestDataGenerator#getValidColorFilesTestData")
    public void excelFileGetsProperlyWritten(String fileName, Schedule initialData) throws IOException {
        final var excelFile = File.createTempFile("report" + System.currentTimeMillis(), ".xlsx", new File("target"));
        excelFile.deleteOnExit();
        scheduleWriter.writeSchedule(new FileOutputStream(excelFile));
        final var importedData = scheduleLoader.loadSchedule(new FileInputStream(excelFile));
        assertThat(initialData).isEqualTo(importedData);
    }
}