package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.exception.InvalidExcelFileException;
import com.endava.tmd.soj.schedulematcher.model.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileScheduleLoader implements ScheduleLoader{

    private static final String INVALID_EXCEL_FILE_EXCEPTION_MESSAGE = "Invalid Excel schedule file";
    private static final Day[] days = Day.values();


    @Override
    public Schedule loadSchedule(InputStream inputStream) {

        try(XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            if (formatIsValid(workbook))
                    return buildSchedule(workbook);

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
    }




    private boolean formatIsValid(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0);

        try(XSSFWorkbook templateWorkbook = new XSSFWorkbook(new FileInputStream("src/main/resources/com/endava/tmd/soj/scheduleTemplate.xlsx"))) {
            XSSFRow row = sheet.getRow(0);

            XSSFSheet templateSheet = templateWorkbook.getSheetAt(0);
            XSSFRow templateRow = templateSheet.getRow(0);


            for (int i = 1; i < 8; i++) {
                XSSFCell cell = row.getCell(i);
                XSSFCell templateCell = templateRow.getCell(i);

                if (!cell.getStringCellValue().equals(templateCell.getStringCellValue()))
                    throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
            }


            for (int i = 1; i < 12; i++) {
                row = sheet.getRow(i);
                templateRow = templateSheet.getRow(i);

                XSSFCell cell = row.getCell(0);
                XSSFCell templateCell = templateRow.getCell(0);

                if (!cell.getStringCellValue().equals(templateCell.getStringCellValue()))
                    throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
    }




    private Schedule buildSchedule(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<ExcelCell> excelCellsList = new ArrayList<>();

        boolean containsXs = false;
        boolean containsColors = false;
        boolean containsGreens = false;

        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getRowIndex() == 0)
                    break;

                if (cell.getColumnIndex() == 0)
                    continue;


                if (cell.getStringCellValue().equals("x"))
                    containsXs = true;
                else if (!cell.getStringCellValue().isEmpty())
                    throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);


                Color cellColor = cell.getCellStyle().getFillForegroundColorColor();
                if (cellColor != null) {
                    containsColors = true;

                    if (matchIntervalColor(((XSSFColor) cellColor).getARGBHex()) == IntervalColor.GREEN) {
                        containsGreens = true;
                    }
                }


                if (containsXs && containsColors) {
                    throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
                }
                else if (containsXs) {
                    ExcelCell excelCell = buildExcelCell(cell, true);
                    excelCellsList.add(excelCell);
                }
                else if (containsColors) {
                    ExcelCell excelCell = buildExcelCell(cell, false);
                    excelCellsList.add(excelCell);
                }
            }
        }


        if (excelCellsList.isEmpty() && containsGreens)
            return new Schedule();

        if (excelCellsList.isEmpty() && !containsColors)
            throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);


        return processExcelCellsList(excelCellsList, containsXs, containsColors);
    }




    private IntervalColor matchIntervalColor(String cellColorARGBHex) {
        String cellColorHex = null;
        if (cellColorARGBHex != null)
            cellColorHex = "#" + cellColorARGBHex.substring(2);

        IntervalColor[] intervalColors = IntervalColor.values();

        for (IntervalColor intervalColor : intervalColors) {
            String intervalColorHex = intervalColor.getCode();
            if (intervalColorHex.equals(cellColorHex))
                return intervalColor;
        }

        throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
    }




    private ExcelCell buildExcelCell(Cell cell, boolean containsXs) {
        ExcelCell excelCell = new ExcelCell();

        excelCell.setRowIndex(cell.getRowIndex());
        excelCell.setColumnIndex(cell.getColumnIndex());

        if (containsXs)
            excelCell.setContent(cell.getStringCellValue());
        else {
            String colorHex = ((XSSFColor) cell.getCellStyle().getFillForegroundColorColor()).getARGBHex();

            if (matchIntervalColor(colorHex) == IntervalColor.YELLOW) {
                excelCell.setColorHex(IntervalColor.YELLOW.getCode());

            } else if (matchIntervalColor(colorHex) == IntervalColor.RED)
                excelCell.setColorHex(IntervalColor.RED.getCode());
        }

        return excelCell;
    }




    private Schedule processExcelCellsList(List<ExcelCell> list, boolean containsXs, boolean containsColors) {
        Schedule schedule = new Schedule();

        for (ExcelCell cell : list) {
            Day day = days[cell.getColumnIndex() - 1];

            IntervalColor color = IntervalColor.GREEN;

            if (containsXs)
                color = IntervalColor.WHITE;
            else if (containsColors && cell.getColorHex() != null){

                if (cell.getColorHex().equals("#FF0000"))
                    color = IntervalColor.RED;
                else if (cell.getColorHex().equals("#FFFF00"))
                    color = IntervalColor.YELLOW;
            }
            else continue;

            TimeInterval timeInterval = new TimeInterval(cell.getRowIndex() + 7, cell.getRowIndex() + 8, color);

            schedule.addBusyTimeInterval(day, timeInterval);
        }

        return schedule;
    }
}
