package com.endava.tmd.soj.schedulematcher.service;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class GridPaneBuilder {

    private int currentRow = 0;
    private final GridPane gridPane;

    public GridPaneBuilder(int width, int height, int padding) {
        this.gridPane = new GridPane();
        gridPane.setPrefSize(width, height);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(padding, padding, padding, padding));
        gridPane.setHgap(padding);
        gridPane.setVgap(padding);
    }

    public void addComponent(Parent parent) {
        gridPane.add(parent, 0, currentRow);
        GridPane.setHalignment(parent, HPos.CENTER);
        this.currentRow++;
    }

    public void addComponents(Parent... parents) {
        for (Parent parent : parents) {
            this.addComponent(parent);
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }

}
