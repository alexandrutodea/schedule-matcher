package com.endava.tmd.soj.schedulematcher.client;

import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.service.ExcelFileScheduleLoader;
import com.endava.tmd.soj.schedulematcher.service.ExcelFileScheduleWriter;
import com.endava.tmd.soj.schedulematcher.service.TemplateFileDownloader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

public class TextualClientApp {

    private static String groupCode;

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        var scanner = new Scanner(System.in);
        System.out.println("Do you want to create a group?");
        boolean input = getYesNoInput(scanner);
        if (input) {
            System.out.println("Group size:");
            int size = readGroupSize(scanner);
            var createGroupThread = new CreateGroupThread("127.0.0.1", 5200, size);
            createGroupThread.start();
            createGroupThread.join();
            groupCode = createGroupThread.getGroupCode();
            System.out.println("Your group code is: " + groupCode);
        }
        System.out.println("Do you want to download the template file");
        input = getYesNoInput(scanner);
        if (input) {
            TemplateFileDownloader.downloadTemplateFile();
            System.out.println("Template file has been downloaded");
        }
        System.out.println("Enter group code:");
        groupCode = scanner.nextLine();
        System.out.println("Enter path to your schedule file:");
        String scheduleFilePath = scanner.nextLine();
        Schedule schedule = new ExcelFileScheduleLoader().loadSchedule(new FileInputStream(scheduleFilePath));
        var joinGroupThread = new JoinGroupThread(groupCode, schedule, "127.0.0.1", 5200);
        joinGroupThread.start();
        joinGroupThread.join();
        var combinedSchedule = joinGroupThread.getCombinedSchedule();
        var excelFileScheduleWriter = new ExcelFileScheduleWriter();
        excelFileScheduleWriter.writeSchedule(new FileOutputStream(getCombinedScheduleDownloadLocation()), combinedSchedule);
    }

    private static int readGroupSize(Scanner scanner) {
        var size = Integer.parseInt(scanner.nextLine());
        while (size < 2 || size > 10) {
            System.out.println("Invalid group size. Try again.");
            size = Integer.parseInt(scanner.nextLine());
        }
        return size;
    }

    private static String getCombinedScheduleDownloadLocation() {
        var currentTimeMillis = System.currentTimeMillis();
        var home = System.getProperty("user.home");
        return home + "/Downloads/schedule_" + currentTimeMillis + ".xlsx";
    }

    private static boolean getYesNoInput(Scanner scanner) {

        System.out.println("Y\\N?");
        var input = scanner.nextLine();

        while (!input.equals("N") && !input.equals("Y")) {
            System.out.println("Invalid input. Try again.");
            input = scanner.nextLine();
        }

        return switch (input) {
            case "Y" -> true;
            default -> false;
        };

    }
}
