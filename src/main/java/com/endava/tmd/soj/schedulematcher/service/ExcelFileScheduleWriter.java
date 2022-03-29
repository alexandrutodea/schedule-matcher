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
            Workbook workbook = new XSSFWorkbook();

            addTemplate(workbook);

            if (schedule.equals(new Schedule()))
                exportEmptySchedule(workbook);
            else
                exportSchedule(schedule, workbook);

            workbook.write(outputStream);

            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void exportSchedule(Schedule schedule, Workbook workbook){
        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

        for (int i = 1; i < 8; i++) {
            if (schedule.getBusyTimeIntervals(days[i - 1]).isEmpty())
                continue;

            List<TimeInterval> timeIntervals = schedule.getBusyTimeIntervals(days[i - 1]).get();

            for (TimeInterval timeInterval : timeIntervals) {
                XSSFRow row = sheet.getRow(timeInterval.getStart() - 7);
                XSSFCell cell = row.createCell(i);
                XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();

                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setFillForegroundColor(getXSSFIntervalColor(timeInterval.getIntervalColor()));

                cell.setCellStyle(cellStyle);
            }
        }
    }




    private void addTemplate(Workbook workbook) {
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);

        row.createCell(1).setCellValue("Monday");
        row.createCell(2).setCellValue("Tuesday");
        row.createCell(3).setCellValue("Wednesday");
        row.createCell(4).setCellValue("Thursday");
        row.createCell(5).setCellValue("Friday");
        row.createCell(6).setCellValue("Saturday");
        row.createCell(7).setCellValue("Sunday");

        for (int i = 1; i < 12; i++) {
            row = sheet.createRow(i);
            String interval = (i + 7) + ":00 - " + (i + 8) + ":00";
            row.createCell(0).setCellValue(interval);
        }
    }




    private XSSFColor getXSSFIntervalColor(IntervalColor intervalColor) {
        XSSFColor color = new XSSFColor();

        switch (intervalColor) {
            case RED -> color.setARGBHex("FF0000");
            case YELLOW -> color.setARGBHex("FFFF00");
            case GREEN -> color.setARGBHex("7CFC00");
            case WHITE -> color.setARGBHex("FFFFFF");
        }

        return color;
    }




    private void exportEmptySchedule(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < 12; i++) {
                Row row = sheet.getRow(i);

                for (int j = 1; j < 8; j++) {
                    Cell cell = row.createCell(j);
                    XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();

                    cellStyle.setFillForegroundColor(getXSSFIntervalColor(IntervalColor.GREEN));
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                    cell.setCellStyle(cellStyle);
                }
            }
    }
}
