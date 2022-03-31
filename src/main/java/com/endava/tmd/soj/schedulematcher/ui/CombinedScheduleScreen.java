package com.endava.tmd.soj.schedulematcher.ui;

import com.endava.tmd.soj.schedulematcher.service.GridPaneBuilder;
import com.endava.tmd.soj.schedulematcher.service.ScreenController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CombinedScheduleScreen {

    private CombinedScheduleScreen() {
        //it makes to instantiate this class
    }

    public static Parent getScreen() {
        var gridPaneBuilder = new GridPaneBuilder(400, 300, 22);

        var combined = new Label("The combined schedule has been generated.");
        var download = new Button("Download");
        download.setPrefSize(125, 25);
        var cancel = new Button("Cancel");
        cancel.setPrefSize(125, 25);

        download.setOnAction(e -> {});
        cancel.setOnAction(e -> ScreenController.displayScreen("main-menu"));

        gridPaneBuilder.addComponents(combined, download, cancel);
        return gridPaneBuilder.getGridPane();
    }

}
