package com.endava.tmd.soj.schedulematcher.ui;

import com.endava.tmd.soj.schedulematcher.service.GridPaneBuilder;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class WaitingScreen {

    private WaitingScreen() {
        //it makes no sense to instantiate this class
    }

    public static Parent getView() {
        var gridPaneBuilder = new GridPaneBuilder(400, 300, 10);
        gridPaneBuilder.addComponents(new Label("Your schedule has been submitted."),
                new Label("The server is currently waiting for the rest of your group."),
                new Label("You will get redirected as soon as everyone submits."));
        return gridPaneBuilder.getGridPane();
    }
}
