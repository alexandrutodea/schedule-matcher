package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.Day;
import com.endava.tmd.soj.schedulematcher.model.IntervalColor;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.model.TimeInterval;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.text.Style;
import java.awt.Color;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ExcelFileScheduleWriter implements ScheduleWriter {

    public static final Day[] days = Day.values();

    @Override
    public void writeSchedule(OutputStream outputStream, Schedule schedule) {

        try {
            Workbook workbook = new XSSFWorkbook();

            addHeaders(workbook);

            if (schedule.equals(new Schedule()))
                checkIfScheduleIsEmpty(workbook);
            else
                exportSchedule(schedule, workbook);

            workbook.write(outputStream);
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO: to fill in with green the free schedule spots
    public void exportSchedule(Schedule schedule, Workbook workbook){
        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

        for (int i = 1; i < 8; i++) {
            if (schedule.getBusyTimeIntervals(days[i - 1]).isEmpty())
                continue;

            List<TimeInterval> timeIntervals = schedule.getBusyTimeIntervals(days[i - 1]).get();

            //row index + 7/8 for interval ends
            for (TimeInterval timeInterval : timeIntervals) {
                XSSFRow row = sheet.getRow(timeInterval.getStart() - 7);
                XSSFCell cell = row.createCell(i);

                //TODO: NPI

                XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();

                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                //TODO: I believe is not setting the colors properly; the color returned in loader is null
                //TODO: getXSSFColor solved the ColorColor() is null error (only if append FF at the end)
//                cellStyle.setFillForegroundColor(new XSSFColor(getIntervalColorRGB(timeInterval.getIntervalColor())));
                cellStyle.setFillForegroundColor(getXSSFIntervalColor(timeInterval.getIntervalColor()));

                cell.setCellStyle(cellStyle);



            }

        }


    }


    private void addHeaders(Workbook workbook) {
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
            String interval = (i+7) + ":00 - " + (i+8) + ":00";
            row.createCell(0).setCellValue(interval);
        }

    }

    private XSSFColor getXSSFIntervalColor(IntervalColor intervalColor) {
        XSSFColor color = new XSSFColor();

        if (intervalColor == IntervalColor.RED) {
            color.setARGBHex("FF0000");
        }
        if (intervalColor == IntervalColor.WHITE) {
            color.setARGBHex("FFFFFF");
        }
        if (intervalColor == IntervalColor.YELLOW) {
            color.setARGBHex("FFFF00");
        }
        if (intervalColor == IntervalColor.GREEN) {
            color.setARGBHex("7CFC00");
        }

        return color;
    }

    private byte[] getIntervalColorRGB(IntervalColor intervalColor) {
        byte[] rgb = new byte[3];
//        rgb[0] = (byte) Integer.parseInt(intervalColor.getCode().substring(1, 3), 16);
//        rgb[1] = (byte) Integer.parseInt(intervalColor.getCode().substring(3, 5), 16);
//        rgb[2] = (byte) Integer.parseInt(intervalColor.getCode().substring(5, 7), 16);

        if (intervalColor == IntervalColor.RED) {
            rgb[0] = (byte) 255;
        }

        if (intervalColor == IntervalColor.YELLOW) {
            rgb[0] = (byte) 255;
            rgb[1] = (byte) 255;
        }

        if (intervalColor == IntervalColor.GREEN) {
            rgb[0] = (byte) 124;
            rgb[1] = (byte) 252;
        }

        if (intervalColor == IntervalColor.WHITE) {
            rgb[0] = (byte) 255;
            rgb[1] = (byte) 255;
            rgb[2] = (byte) 255;
        }

        return rgb;
    }

    private void checkIfScheduleIsEmpty(Workbook workbook) {
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
