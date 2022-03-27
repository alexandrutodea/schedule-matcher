package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.exception.InvalidExcelFileException;
import com.endava.tmd.soj.schedulematcher.model.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelFileScheduleLoader implements ScheduleLoader{

    private static final String INVALID_EXCEL_FILE_EXCEPTION_MESSAGE = "Invalid Excel schedule file";
    private static final Day[] days = Day.values();

    @Override
    public Schedule loadSchedule(InputStream inputStream) {

        try(XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            if (validateFormat(workbook))
                    return buildSchedule(workbook);

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
    }


    //not checking for CellStyle
    public boolean validateFormat(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0);

        try(XSSFWorkbook templateWorkbook = new XSSFWorkbook(new FileInputStream("src/main/resources/com/endava/tmd/soj/scheduleTemplate.xlsx"))) {
            XSSFRow row = sheet.getRow(0);

            XSSFSheet templateSheet = templateWorkbook.getSheetAt(0);
            XSSFRow templateRow = templateSheet.getRow(0);

            XSSFCell cell;
            XSSFCell templateCell;
            boolean equals;
            for (int i = 1; i < 8; i++) {
                cell = row.getCell(i);
                templateCell = templateRow.getCell(i);

                equals = cell.getStringCellValue().equals(templateCell.getStringCellValue());
                if (!equals)
                    throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
            }

            for (int i = 1; i < 12; i++) {
                row = sheet.getRow(i);
                templateRow = templateSheet.getRow(i);

                cell = row.getCell(0);
                templateCell = templateRow.getCell(0);

                equals = cell.getStringCellValue().equals(templateCell.getStringCellValue());
                if (!equals)
                    throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
    }


//    private boolean isFileEmpty(XSSFWorkbook workbook) {
//        XSSFSheet sheet = workbook.getSheetAt(0);
//
//        XSSFRow row = sheet.getRow(0);
//        sheet.removeRow(row);
//
//        //Removed row(0) -> starting from row(1)
//        for (int i = 1; i < 12; i++) {
//            row = sheet.getRow(i);
//            row.removeCell(row.getCell(0));
//        }
//
//        return (sheet.getLastRowNum() == 0 && sheet.getRow(0) == null);
//    }


    private Schedule buildSchedule(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<ExcelCell> excelCellsList = new ArrayList<>();

        boolean containsXs = false;
        boolean containsColors = false;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getRowIndex() == 0)
                    break;

                if (cell.getColumnIndex() == 0)
                    continue;
//
//                String colorHex = ((XSSFColor) cell.getCellStyle().getFillBackgroundColorColor()).getARGBHex();
//                ExcelCell excelCell = new ExcelCell(cell.getRowIndex(), cell.getColumnIndex(), cell.getStringCellValue(), colorHex);

                if (cell.getStringCellValue().equals("x"))
                    containsXs = true;
                //if it contains another string other than 'x'
                else if (!cell.getStringCellValue().isEmpty())
                    throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);

                //assumption that the cell is styled only with colors
                //TODO: to check if matches the IntervalColors colors
                Color checkedColor = cell.getCellStyle().getFillForegroundColorColor();
                String colorHex;
                if (checkedColor != null) {
                    containsColors = true;

                    //fails with FillBackgroundColor
                    Color cellColor = cell.getCellStyle().getFillForegroundColorColor();
                    //String colorHex = (XSSFCell) cell.getCellStyle().getFillForegroundColorColor().;
                    colorHex = ((XSSFColor) cellColor).getARGBHex();

                    //if-clause for different colors
                    if (matchIntervalColor(colorHex) == null)
                        throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);

                }

                //TODO: check here for ValidColor tests
                if (containsXs && containsColors) {
                    throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
                }
                else if (containsXs && !containsColors) {
                    ExcelCell excelCell = buildExcelCell(cell, true);
                    excelCellsList.add(excelCell);
                }
                else if (!containsXs && containsColors) {
                    ExcelCell excelCell = buildExcelCell(cell, false);

                    if (matchIntervalColor(((XSSFColor) cell.getCellStyle().getFillForegroundColorColor()).getARGBHex()) == IntervalColor.YELLOW) {
//                        || matchIntervalColor(((XSSFColor) cell.getCellStyle().getFillForegroundColorColor()).getARGBHex()) == IntervalColor.RED) {
                        excelCell.setColorHex(IntervalColor.YELLOW.getCode());
                    } else if (matchIntervalColor(((XSSFColor) cell.getCellStyle().getFillForegroundColorColor()).getARGBHex()) == IntervalColor.RED)
                        excelCell.setColorHex(IntervalColor.RED.getCode());


                    excelCellsList.add(excelCell);

                }

            }
        }

        //checks if schedule file is empty OR contains green cells only
        if (excelCellsList.isEmpty() && !containsColors)
            throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);

        return processExcelCellsList(excelCellsList, containsXs, containsColors);
    }


    //use row/cell index to determine day/interval
    //row index + 7/8; column index - 1
    public Schedule processExcelCellsList(List<ExcelCell> list, boolean containsXs, boolean containsColors) {
        Schedule schedule = new Schedule();

        TimeInterval timeInterval;
        Day day;
        for (ExcelCell cell : list) {
            day = days[cell.getColumnIndex() - 1];

            IntervalColor color = IntervalColor.GREEN;
            if (containsXs) //TODO: get back to this; passes the tests though
                color = IntervalColor.WHITE;
            else if (containsColors && cell.getColorHex() != null){
                //set the color of the cell
//                color = matchIntervalColorRgbHex(cell.getColorHex());

                if (cell.getColorHex().equals("#FF0000"))
                    color = IntervalColor.RED;
                else if (cell.getColorHex().equals("#FFFF00"))
                    color = IntervalColor.YELLOW;
            }
            else continue;
//            else throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);


            timeInterval = new TimeInterval(cell.getRowIndex() + 7, cell.getRowIndex() + 8, color);

            schedule.addBusyTimeInterval(day, timeInterval);
        }

        return schedule;
    }


    public IntervalColor matchIntervalColor(String cellColorARGBHex) {
        String cellColorHex = null;
        if (cellColorARGBHex != null)
            cellColorHex = "#" + cellColorARGBHex.substring(2);

        IntervalColor[] intervalColors = IntervalColor.values();

        for (IntervalColor intervalColor : intervalColors) {
            String intervalColorHex = intervalColor.getCode();
            if (intervalColorHex.equals(cellColorHex))
                return intervalColor;
        }
//
        throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
//        return null;
    }

//    //TODO: !
//    private IntervalColor matchIntervalColorRgbHex(String cellColorHex) {
//        IntervalColor[] intervalColors = IntervalColor.values();
//
//        for (IntervalColor intervalColor : intervalColors) {
//            String intervalColorHex = intervalColor.getCode();
//            if (intervalColorHex.equals(cellColorHex))
//                return intervalColor;
//        }
//
//        throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
//
//    }


    public ExcelCell buildExcelCell(Cell cell, boolean containsXs) {
        ExcelCell excelCell = new ExcelCell();

        excelCell.setRowIndex(cell.getRowIndex());
        excelCell.setColumnIndex(cell.getColumnIndex());

        if (containsXs)
            excelCell.setContent(cell.getStringCellValue());
        else
            excelCell.setColorHex(((XSSFColor) cell.getCellStyle().getFillBackgroundColorColor()).getARGBHex());

        return excelCell;
    }
}
