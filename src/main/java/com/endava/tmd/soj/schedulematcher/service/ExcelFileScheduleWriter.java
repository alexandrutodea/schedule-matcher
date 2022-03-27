package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.Day;
import com.endava.tmd.soj.schedulematcher.model.IntervalColor;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.model.TimeInterval;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ExcelFileScheduleWriter implements ScheduleWriter {

    public static final Day[] days = Day.values();

    @Override
    public void writeSchedule(OutputStream outputStream, Schedule schedule) {

        File template = new File("src/main/resources/com/endava/tmd/soj/scheduleTemplate.xlsx");
        File export = new File("target/export.xlsx");

        try {
            Files.copy(template.toPath(), export.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try(XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("target/export.xlsx"))) {
            exportSchedule(schedule, workbook);

            workbook.write(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    public void exportSchedule(Schedule schedule, Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i < 8; i++) {
            if (schedule.getBusyTimeIntervals(days[i - 1]).isEmpty())
                continue;

            List<TimeInterval> timeIntervals = schedule.getBusyTimeIntervals(days[i - 1]).get();

            //row index + 7/8 for interval ends
            for (TimeInterval timeInterval : timeIntervals) {
                Row row = sheet.getRow(timeInterval.getStart() - 7);
                Cell cell = row.getCell(i);

                //TODO: NPI
                if (cell != null) {
                    XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
                    cellStyle.setFillForegroundColor(new XSSFColor(getIntervalColorRGB(timeInterval.getIntervalColor())));

                    cell.setCellStyle(cellStyle);
                }


            }

        }


    }

    private byte[] getIntervalColorRGB(IntervalColor intervalColor) {
        byte[] rgb = new byte[3];
        rgb[0] = (byte) Integer.parseInt(intervalColor.getCode().substring(1, 3), 16);
        rgb[1] = (byte) Integer.parseInt(intervalColor.getCode().substring(3, 5), 16);
        rgb[2] = (byte) Integer.parseInt(intervalColor.getCode().substring(5, 7), 16);

        return rgb;
    }
}
