package com.endava.tmd.soj.schedulematcher.ui;

import com.endava.tmd.soj.schedulematcher.service.GridPaneBuilder;
import com.endava.tmd.soj.schedulematcher.service.ScreenController;
import com.endava.tmd.soj.schedulematcher.service.TemplateFileDownloader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.*;

public class DownloadTemplateScreen {

    private DownloadTemplateScreen() {
        //it makes no sense to instantiate this class
    }

    public static Parent getScreen() {

        var gridPaneBuilder = new GridPaneBuilder(400, 300, 25);

        var download = new Button("Download");
        download.setPrefSize(125, 25);

        var skip = new Button("Skip");
        skip.setPrefSize(125, 25);

        var cancel = new Button("Cancel");
        cancel.setPrefSize(125, 25);

        TextFlow downloadFlow = new TextFlow();
        downloadFlow.setTextAlignment(TextAlignment.CENTER);
        Text downloadText = new Text("Download the ");
        downloadText.setFont(Font.font("Verdana", 12));
        Text xlsx = new Text(".xlsx");
        xlsx.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        Text file = new Text(" file.");
        file.setFont(Font.font("Verdana", 12));
        downloadFlow.getChildren().addAll(downloadText, xlsx, file);

        var putXFlow = new TextFlow();
        putXFlow.setTextAlignment(TextAlignment.CENTER);
        var put = new Text("Put an ");
        put.setFont(Font.font("Verdana", 12));
        var x = new Text("\"x\"");
        x.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        var busyIntervals = new Text(" for the busy intervals.");
        busyIntervals.setFont(Font.font("Verdana", 12));
        putXFlow.getChildren().addAll(put, x, busyIntervals);

        download.setOnAction(e -> TemplateFileDownloader.downloadTemplateFile());
        skip.setOnAction(e -> ScreenController.displayScreen("select-file"));
        cancel.setOnAction(e -> ScreenController.displayScreen("main-menu"));

        gridPaneBuilder.addComponents(downloadFlow, putXFlow, download, skip, cancel);

        return gridPaneBuilder.getGridPane();

    }
}
