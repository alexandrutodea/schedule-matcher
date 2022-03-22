package com.endava.tmd.soj.schedulematcher.service;

import java.io.File;

class Utils {

    private Utils() {
        //it makes no sense to instantiate this class
    }

    private static final String TEST_FILES_EXT = ".xlsx";

    public static String buildFilePath(Directory directory, String fileName) {
        return directory.getDirectoryPath() + "/" + fileName + TEST_FILES_EXT;
    }

    public static String generateTestDisplayName(String fileName) {
        return String.join(" ", fileName.split("[A-Z]"));
    }

}
