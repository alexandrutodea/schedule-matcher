package com.endava.tmd.soj.schedulematcher.ui;

import com.endava.tmd.soj.schedulematcher.client.GraphicalClientApp;
import com.endava.tmd.soj.schedulematcher.service.ExcelFileScheduleWriter;
import com.endava.tmd.soj.schedulematcher.service.GridPaneBuilder;
import com.endava.tmd.soj.schedulematcher.service.ScreenController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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

        download.setOnAction(e -> {
            try {
                var excelFileScheduleWriter = new ExcelFileScheduleWriter();
                excelFileScheduleWriter.writeSchedule(new FileOutputStream(getCombinedScheduleDownloadLocation()),
                        GraphicalClientApp.getCombinedSchedule());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        cancel.setOnAction(e -> ScreenController.displayScreen("main-menu"));

        gridPaneBuilder.addComponents(combined, download, cancel);
        return gridPaneBuilder.getGridPane();
    }

    private static String getCombinedScheduleDownloadLocation() {
        var currentTimeMillis = System.currentTimeMillis();
        var home = System.getProperty("user.home");
        return home + "/Downloads/schedule_" + currentTimeMillis + ".xlsx";
    }

}
