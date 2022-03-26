package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.exception.InvalidExcelFileException;
import com.endava.tmd.soj.schedulematcher.model.Day;
import com.endava.tmd.soj.schedulematcher.model.IntervalColor;
import com.endava.tmd.soj.schedulematcher.model.Schedule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.endava.tmd.soj.schedulematcher.model.TimeInterval;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;


public class ExcelFileScheduleLoader implements ScheduleLoader{

    static final String EXCEPTION_MESSAGE = "***********";

    Day[] days = Day.values();
    String[] intervalColors = {"#FF0000", "#FFFF00", "#7CFC00", "#FFFFFF"};


    @Override
    public Schedule loadSchedule(InputStream inputStream) {
        //throw new UnsupportedOperationException();

        try(XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream)) {
            Schedule schedule = new Schedule();
//            if (validateHeaders(workbook))
                buildSchedule(workbook, schedule);
            
            return schedule;

        } catch (IOException e) {
            e.printStackTrace();
        }


        throw new InvalidExcelFileException("headers not valid");

    }


    //TODO: to fix validation;  not working at all for non color tests
    public boolean validateHeaders(Workbook workbook) throws IOException {
        Sheet sheet = workbook.getSheetAt(0);

        XSSFWorkbook templateWorkbook = new XSSFWorkbook(new FileInputStream("src/main/resources/com/endava/tmd/soj/scheduleTemplate.xlsx"));
        Sheet templateSheet = templateWorkbook.getSheetAt(0);

        Cell cell;
        Cell templateCell;
        boolean equals;

        //TODO: update Excel template
        Row row = sheet.getRow(0);
        Row templateRow = templateSheet.getRow(0);

        for (int cellNumber = 1; cellNumber < 8; cellNumber++) {
            cell = row.getCell(cellNumber);
            templateCell = templateRow.getCell(cellNumber);

            equals = cell.getRichStringCellValue().getString()
                    .equals(templateCell.getRichStringCellValue().getString());

            if (!equals)
                throw new InvalidExcelFileException("Invalid template weekdays");
        }

        //TODO: change to rownumber < 12 when Excel files get fixed
        for (int rowNumber = 1; rowNumber < 11; rowNumber++) {
            row = sheet.getRow(rowNumber);
            templateRow = templateSheet.getRow(rowNumber);

            equals = row.getCell(0).getRichStringCellValue().getString()
                    .equals(templateRow.getCell(0).getRichStringCellValue().getString());

            if (!equals)
                throw new InvalidExcelFileException("Invalid template intervals");

            //TODO: maybe update the tests
        }

        return true;
    }



    public void buildSchedule(XSSFWorkbook workbook, Schedule schedule) {

        XSSFSheet sheet = workbook.getSheetAt(0);

        boolean fileContainsColors = false;
        boolean fileContainsXs = false;

        //TODO: fix Excel File template
        for (int i = 1; i <= 7; i++) {
            Day day = days[i - 1];

            for (int j = 1; j <= 10; j++) {
                XSSFRow row = sheet.getRow(j);
                XSSFCell cell = row.getCell(i);

//                boolean cellIsEmpty = false; //= cell.getRichStringCellValue().getString().isEmpty();
                boolean cellContainsX = false; //cell.getRichStringCellValue().getString().equals("x");

                //TODO: fix hasDefaultColor
                boolean cellHasDefaultColor = false; //cell.getCellStyle().getFillBackgroundColor() == 0;

                if (cell == null || cell.getCellType() == CellType.BLANK) {
                    continue;
                } else {
                    if (cell.getRichStringCellValue().getString().equals("x"))
                        cellContainsX = true;
                    if (cell.getCellStyle().getFillBackgroundColor() == IndexedColors.AUTOMATIC.getIndex())
                        cellHasDefaultColor = true;
                }


                //TODO: to fix ColorInput files - empty Schedule returned; works for X-marked files

                if (cellContainsX && cellHasDefaultColor) {
                    fileContainsXs = true;

                    int[] interval = extractInterval(cell, sheet);
                    schedule.addBusyTimeInterval(day, new TimeInterval(interval[0], interval[1]));
                }
                else {//if (!cellContainsX && !cellHasDefaultColor && cellHasAvailabilityColor(cell)) {
                    fileContainsColors = true;

                    int[] interval = extractInterval(cell, sheet);
                    schedule.addBusyTimeInterval(day, new TimeInterval(interval[0], interval[1], getAvailabilityColor(cell)));
                }
//                else throw new InvalidExcelFileException("invalid input - Xs/Colors");


                if (fileContainsColors && fileContainsXs)
                    throw new InvalidExcelFileException("file contains both Xs and Colors");
            }

        }


    }


    //TODO: check getColor method

    public IntervalColor getAvailabilityColor(XSSFCell cell) {
        XSSFColor cellColor = cell.getCellStyle().getFillBackgroundColorColor();

        //TODO: rgb vs argb
        for (IntervalColor color : IntervalColor.values()) {
            if (cellColor.getARGBHex().equals(color.getCode()))
                return color;
        }


        throw new InvalidExcelFileException("A cell may only be colored in either white, red, green or yellow");
    }
//
//
//    //TODO: dupe
//    public byte[] getSpecificForegroundColor(IntervalColor intervalColor) {
//        byte[] rgb = new byte[3];
//        rgb[0] = Byte.valueOf(intervalColor.getCode().substring(1, 3), 16);
//        rgb[1] = Byte.valueOf(intervalColor.getCode().substring(3, 5), 16);
//        rgb[2] = Byte.valueOf(intervalColor.getCode().substring(5, 7), 16);
//
//        return rgb;
//    }


    public boolean cellHasAvailabilityColor(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK)
            return true;

        String colorHex = (( XSSFColor) cell.getCellStyle().getFillBackgroundColorColor()).getARGBHex();

        for (String color : intervalColors) {
            if (colorHex.equals(color))
                return true;
        }

        return false;
    }


    public int[] extractInterval(Cell cell, Sheet sheet) {

        if (cell != null) {
            Row row = sheet.getRow(cell.getRowIndex());

            Cell intervalCell = row.getCell(0);

            String[] intervals = intervalCell.getRichStringCellValue().getString().split(" - ");

            String[] tempIntervals;
            int[] finalIntervals = new int[2];

            tempIntervals = intervals[0].split(":", 2);
            finalIntervals[0] = Integer.parseInt(tempIntervals[0]);

            tempIntervals = intervals[1].split(":", 2);
            finalIntervals[1] = Integer.parseInt(tempIntervals[0]);

            return finalIntervals;

        } else
            throw new InvalidExcelFileException("invalid interval format");


    }

}
