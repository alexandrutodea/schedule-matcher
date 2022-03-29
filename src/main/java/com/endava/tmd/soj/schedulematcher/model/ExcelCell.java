package com.endava.tmd.soj.schedulematcher.model;

/**
 * The {@code ExcelCell} class represents an entity that holds information about the cell to be created within the Excel file.
 */
public class ExcelCell {

    private int rowIndex;
    private int columnIndex;
    private String content;
    private String colorHex;

    public ExcelCell() {
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }
}
