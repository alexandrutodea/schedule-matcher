package com.endava.tmd.soj.schedulematcher.model;

/**
 * The {@code Color} enumeration holds the colors for different levels of availability in a specific timeslot
 * RED - 2 or more people unavailable
 * YELLOW - 1 person unavailable
 * GREEN - everyone is available
 * WHITE - people's availability hasn't been determined yet
 */
public enum IntervalColor {

    RED("#FF0000"),
    YELLOW("#FFFF00"),
    GREEN("#7CFC00"),
    WHITE("#FFFFFF");

    private final String code;

    IntervalColor(String code) {
        this.code = code;
    }

    /**
     * @return hexadecimal code for the color
     */
    public String getCode() {
        return this.code;
    }

}
