package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.exception.InvalidExcelFileException;
import com.endava.tmd.soj.schedulematcher.model.*;
import org.apache.poi.ss.usermodel.Cell;
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

        try(var workbook = new XSSFWorkbook(inputStream)) {
            if (formatIsValid(workbook))
                    return buildSchedule(workbook);

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
    }


    /**
     * Checks if the input document follows the expected template - i.e., the Monday-Sunday headers and the predefined time intervals.
     * @param workbook Excel document received as input.
     * @return <code>true</code> or <code>throws</code> {@link InvalidExcelFileException}.
     */
    private boolean formatIsValid(XSSFWorkbook workbook) {
        var sheet = workbook.getSheetAt(0);

        try(var templateWorkbook = new XSSFWorkbook(new FileInputStream("src/main/resources/com/endava/tmd/soj/scheduleTemplate.xlsx"))) {
            var row = sheet.getRow(0);

            var templateSheet = templateWorkbook.getSheetAt(0);
            var templateRow = templateSheet.getRow(0);


            for (int i = 1; i < 8; i++) {
                var cell = row.getCell(i);
                var templateCell = templateRow.getCell(i);

                if (!cell.getStringCellValue().equals(templateCell.getStringCellValue()))
                    throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
            }


            for (int i = 1; i < 12; i++) {
                row = sheet.getRow(i);
                templateRow = templateSheet.getRow(i);

                var cell = row.getCell(0);
                var templateCell = templateRow.getCell(0);

                if (!cell.getStringCellValue().equals(templateCell.getStringCellValue()))
                    throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
    }


    /**
     * Iterates through the Excel document and scans its cells
     * in order to construct a {@link Schedule} object.
     * @param workbook Excel document received as input.
     * @return {@link Schedule} object that mirrors the input file.
     */
    private Schedule buildSchedule(XSSFWorkbook workbook) {
        var sheet = workbook.getSheetAt(0);
        List<ExcelCell> excelCellsList = new ArrayList<>();

        var containsXs = false;
        var containsColors = false;
        var containsGreens = false;

        for (var row : sheet) {
            for (var cell : row) {
                if (cell.getRowIndex() == 0)
                    break;

                if (cell.getColumnIndex() == 0)
                    continue;


                if (cell.getStringCellValue().equals("x"))
                    containsXs = true;
                else if (!cell.getStringCellValue().isEmpty())
                    throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);


                var cellColor = cell.getCellStyle().getFillForegroundColorColor();
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
                    var excelCell = buildExcelCell(cell, true);
                    excelCellsList.add(excelCell);
                }
                else if (containsColors) {
                    var excelCell = buildExcelCell(cell, false);
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


    /**
     * Verifies if the fill foreground color of the specific cell
     * matches with one of the predefined colors of {@link IntervalColor}.
     * @param cellColorARGBHex ARGB value in hex string format of the cell's fill foreground color
     * @return {@link IntervalColor} value
     */
    private IntervalColor matchIntervalColor(String cellColorARGBHex) {
        var cellColorHex = "";
        if (cellColorARGBHex != null) {
            cellColorHex = "#" + cellColorARGBHex.substring(2);

            IntervalColor[] intervalColors = IntervalColor.values();

            for (var intervalColor : intervalColors) {
                var intervalColorHex = intervalColor.getCode();
                if (intervalColorHex.equals(cellColorHex))
                    return intervalColor;
            }
        }

        throw new InvalidExcelFileException(INVALID_EXCEL_FILE_EXCEPTION_MESSAGE);
    }


    /**
     * Constructs the {@link ExcelCell} object to be later processed into {@link Schedule}.
     * @param cell {@link Cell} object to be processed into {@link ExcelCell}
     * @param containsXs <code>boolean</code> object representing whether the input file
     *                   contains 'x'-marked intervals
     * @return {@link ExcelCell} object
     */
    private ExcelCell buildExcelCell(Cell cell, boolean containsXs) {
        var excelCell = new ExcelCell();

        excelCell.setRowIndex(cell.getRowIndex());
        excelCell.setColumnIndex(cell.getColumnIndex());

        if (containsXs)
            excelCell.setContent(cell.getStringCellValue());
        else {
            var colorHex = ((XSSFColor) cell.getCellStyle().getFillForegroundColorColor()).getARGBHex();

            if (matchIntervalColor(colorHex) == IntervalColor.YELLOW) {
                excelCell.setColorHex(IntervalColor.YELLOW.getCode());

            } else if (matchIntervalColor(colorHex) == IntervalColor.RED)
                excelCell.setColorHex(IntervalColor.RED.getCode());
        }

        return excelCell;
    }


    /**
     * Constructs the final {@link Schedule} object
     * @param list {@link List} of {@link ExcelCell} objects to be processed into the final {@link Schedule} object.
     * @param containsXs <code>boolean</code> object representing whether the input file
     *                    contains 'x'-marked intervals
     * @param containsColors <code>boolean</code> object representing whether the input file
     *                       contains intervals marked with colors
     * @return the final {@link Schedule} object
     */
    private Schedule processExcelCellsList(List<ExcelCell> list, boolean containsXs, boolean containsColors) {
        var schedule = new Schedule();

        for (var cell : list) {
            var day = days[cell.getColumnIndex() - 1];

            var color = IntervalColor.GREEN;

            if (containsXs)
                color = IntervalColor.WHITE;
            else if (containsColors && cell.getColorHex() != null){

                if (cell.getColorHex().equals("#FF0000"))
                    color = IntervalColor.RED;
                else if (cell.getColorHex().equals("#FFFF00"))
                    color = IntervalColor.YELLOW;
            }
            else continue;

            var timeInterval = new TimeInterval(cell.getRowIndex() + 7, cell.getRowIndex() + 8, color);

            schedule.addBusyTimeInterval(day, timeInterval);
        }

        return schedule;
    }
}
