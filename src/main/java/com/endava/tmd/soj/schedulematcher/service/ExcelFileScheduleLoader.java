package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.exception.InvalidExcelFileException;
import com.endava.tmd.soj.schedulematcher.model.Schedule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelFileScheduleLoader implements ScheduleLoader{

    @Override
    public Schedule loadSchedule(InputStream inputStream) {
        //throw new UnsupportedOperationException();

        try(Workbook workbook = WorkbookFactory.create(inputStream)) {
            if (!validateHeaders(workbook))
                throw new InvalidExcelFileException("Invalid Excel file format");

        } catch (IOException e) {
            e.printStackTrace();
        }


        return new Schedule();
    }



    public boolean validateHeaders(Workbook workbook) throws IOException {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);

        Workbook templateWorkbook = new XSSFWorkbook(new FileInputStream("src/main/resources/com/endava/tmd/soj/scheduleTemplate.xlsx"));
        Sheet templateSheet = templateWorkbook.getSheetAt(0);
        Row templateRow = templateSheet.getRow(0);

        Cell cell;
        Cell templateCell;


        for (int cellNumber = 1; cellNumber < 8; cellNumber++) {
            cell = row.getCell(cellNumber);
            templateCell = templateRow.getCell(cellNumber);

            boolean equals = cell.getRichStringCellValue().getString()
                    .equals(templateCell.getRichStringCellValue().getString());

            if (!equals)
                return false;
        }

        for (int rowNumber = 1; rowNumber < 12; rowNumber++) {
            row = sheet.getRow(rowNumber);
            templateRow = templateSheet.getRow(rowNumber);

            boolean equals = row.getCell(0).getRichStringCellValue().getString()
                    .equals(templateRow.getCell(0).getRichStringCellValue().getString());

            if (!equals)
                return false;
        }

        return true;
    }


}
