package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.Day;
import com.endava.tmd.soj.schedulematcher.model.IntervalColor;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.model.TimeInterval;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.List;

public class ExcelFileScheduleWriter implements ScheduleWriter {

    public static final Day[] days = Day.values();

    @Override
    public void writeSchedule(OutputStream outputStream, Schedule schedule) {

        try {
            var workbook = new XSSFWorkbook();

            addTemplate(workbook);

            exportSchedule(schedule, workbook);

            workbook.write(outputStream);

            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Exports the {@link Schedule} object to the Excel document.
     * @param schedule {@link Schedule}
     * @param workbook output Excel file
     */
    private void exportSchedule(Schedule schedule, Workbook workbook){
        var sheet = (XSSFSheet) workbook.getSheetAt(0);

        for (int i = 1; i < 8; i++) {
            if (schedule.getBusyTimeIntervals(days[i - 1]).isEmpty())
                continue;

            List<TimeInterval> timeIntervals = schedule.getBusyTimeIntervals(days[i - 1]).get();

            for (var timeInterval : timeIntervals) {
                var row = sheet.getRow(timeInterval.getStart() - 7);
                var cell = row.createCell(i);
                var cellStyle = (XSSFCellStyle) workbook.createCellStyle();

                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setFillForegroundColor(getXSSFIntervalColor(timeInterval.getIntervalColor()));

                cell.setCellStyle(cellStyle);
            }

            sheet.autoSizeColumn(i);
        }

        fillInEmptySlots(workbook);
    }

    /**
     * Iterates over the time slots and fills in the unmodified ones with {@link IntervalColor}.GREEN.
     * @param workbook {@link Workbook} object
     */
    public void fillInEmptySlots(Workbook workbook) {
        var sheet = workbook.getSheetAt(0);

        for (int i = 1; i < 12; i++) {
            var row = sheet.getRow(i);

            for (int j = 1; j < 8; j++) {
                var cell = row.getCell(j);
                if (cell == null) {
                    cell = row.createCell(j);

                    var cellStyle = (XSSFCellStyle) workbook.createCellStyle();

                    cellStyle.setFillForegroundColor(getXSSFIntervalColor(IntervalColor.GREEN));
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                    cell.setCellStyle(cellStyle);
                }
            }
        }
    }


    /**
     * Adds the predefined header and time intervals to the output Excel file.
     * @param workbook output Excel document
     */
    private void addTemplate(Workbook workbook) {
        var sheet = workbook.createSheet();
        var row = sheet.createRow(0);

        row.createCell(1).setCellValue("Monday");
        row.createCell(2).setCellValue("Tuesday");
        row.createCell(3).setCellValue("Wednesday");
        row.createCell(4).setCellValue("Thursday");
        row.createCell(5).setCellValue("Friday");
        row.createCell(6).setCellValue("Saturday");
        row.createCell(7).setCellValue("Sunday");

        for (int i = 1; i < 12; i++) {
            row = sheet.createRow(i);
            var interval = (i + 7) + ":00 - " + (i + 8) + ":00";
            row.createCell(0).setCellValue(interval);
        }

        sheet.autoSizeColumn(0);
    }


    /**
     * Converts an {@link IntervalColor} into a {@link XSSFColor},
     * to be applied to {@link CellStyle} object.
     * @param intervalColor {@link IntervalColor} object
     * @return {@link XSSFColor} object
     */
    private XSSFColor getXSSFIntervalColor(IntervalColor intervalColor) {
        var color = new XSSFColor();

        switch (intervalColor) {
            case RED -> color.setARGBHex("FF0000");
            case YELLOW -> color.setARGBHex("FFFF00");
            case GREEN -> color.setARGBHex("7CFC00");
            case WHITE -> color.setARGBHex("FFFFFF");
        }

        return color;
    }
}
