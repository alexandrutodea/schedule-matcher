package com.endava.tmd.soj.schedulematcher.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TemplateFileDownloader {

    private TemplateFileDownloader() {
        //it makes no sense to instantiate this class
    }

    public static void downloadTemplateFile() {
        var property = System.getProperty("user.home");
        var original = Paths.get("src/main/resources/com/endava/tmd/soj/scheduleTemplate.xlsx");
        Path copied = Paths.get(property + "/Downloads/schedule_template.xlsx");
        try {
            Files.copy(original, copied, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
