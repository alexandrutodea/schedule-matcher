package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.Day;
import com.endava.tmd.soj.schedulematcher.model.IntervalColor;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.model.TimeInterval;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

public class ExcelFileScheduleWriter implements ScheduleWriter {

    Day[] days = Day.values();

    @Override
    public void writeSchedule(OutputStream outputStream, Schedule schedule) {
        XSSFWorkbook workbook;

        try {
            workbook = new XSSFWorkbook(new FileInputStream("src/main/resources/com/endava/tmd/soj/scheduleTemplate.xlsx"));

            updateWorkbook(workbook, schedule);

            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO: look into XSSFSheet, XSSFRow, etc
    public void updateWorkbook(XSSFWorkbook workbook, Schedule schedule) {
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= 7; i++) {
            Day day = days[i - 1];

            if (schedule.getBusyTimeIntervals(day).isEmpty())
                continue;

            List<TimeInterval> busyTimeIntervals = schedule.getBusyTimeIntervals(day).get();

            for (int j = 1; j <= 10; j++) {
                Cell cell = sheet.getRow(j).getCell(i);

                String interval = sheet.getRow(j).getCell(0).getRichStringCellValue().getString();

                XSSFCellStyle style = workbook.createCellStyle();
                TimeInterval timeInterval = constructTimeInterval(interval);
                //TODO: research using custom colors (from IntervalColor)
                if (busyTimeIntervals.contains(timeInterval)) {
                    style.setFillForegroundColor(new XSSFColor(getSpecificForegroundColor(timeInterval.getIntervalColor())));
                }
                else {
                    style.setFillForegroundColor(new XSSFColor(getSpecificForegroundColor(IntervalColor.GREEN)));
                }

                cell.setCellStyle(style);

            }
        }


    }


    public byte[] getSpecificForegroundColor(IntervalColor intervalColor) {
        byte[] rgb = new byte[3];
        rgb[0] = Byte.valueOf(intervalColor.getCode().substring(1, 3), 16);
        rgb[1] = Byte.valueOf(intervalColor.getCode().substring(3, 5), 16);
        rgb[2] = Byte.valueOf(intervalColor.getCode().substring(5, 7), 16);

        return rgb;
    }

//    public String constructStringInterval(TimeInterval timeInterval) {
//        return timeInterval.getStart() + ":00 - " + timeInterval.getEnd() + ":00";
//    }


    //TODO: check duplicated code
    public TimeInterval constructTimeInterval(String timeInterval) {
        String[] intervals = timeInterval.split(" - ");

        String[] tempIntervals;
        int[] finalIntervals = new int[2];

        tempIntervals = intervals[0].split(":", 2);
        finalIntervals[0] = Integer.parseInt(tempIntervals[0]);

        tempIntervals = intervals[1].split(":", 2);
        finalIntervals[1] = Integer.parseInt(tempIntervals[0]);

        return new TimeInterval(finalIntervals[0], finalIntervals[1]);
    }
}
