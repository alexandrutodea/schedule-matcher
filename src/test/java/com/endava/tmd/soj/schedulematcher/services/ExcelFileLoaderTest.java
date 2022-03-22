package com.endava.tmd.soj.schedulematcher.services;

import com.endava.tmd.soj.schedulematcher.model.Day;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.model.TimeInterval;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExcelFileLoaderTest {

    private static final String INVALID_FILES_DIR = "src/test/resources/invalid";
    private static final String VALID_FILES_DIR = "src/test/resources/valid";
    private static final String TEST_FILES_EXT = ".xlsx";

    @Test
    void test1() throws IOException {
        var testFile = new FileInputStream("src/test/resources/invalid/emptySchedule.xlsx");
        var workbook = new XSSFWorkbook(testFile);
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;

        for (Row row : sheet) {
            data.put(i, new ArrayList<>());
            for (Cell cell : row) {
                data.get(i).add(cell.getStringCellValue());
            }
            i++;
        }

        System.out.println(data);
    }

    private Schedule buildSchedule(Map<Day, List<TimeInterval>> busyIntervals) {
        var schedule = new Schedule();

        busyIntervals
                .forEach((key, value) -> value
                        .forEach(interval -> schedule
                                .addBusyTimeInterval(key, interval)));

        return schedule;
    }

}