package com.endava.tmd.soj.schedulematcher.service;

public enum Directory {

    INVALID("src/test/resources/invalid"),
    VALID_COLOR("src/test/resources/valid/color"),
    VALID_NON_COLOR("src/test/resources/valid/non-color");

    private final String directoryPath;

    Directory(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

}
