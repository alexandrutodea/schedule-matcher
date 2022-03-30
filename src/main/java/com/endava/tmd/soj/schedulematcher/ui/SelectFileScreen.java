package com.endava.tmd.soj.schedulematcher.ui;

import com.endava.tmd.soj.schedulematcher.service.GridPaneBuilder;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SelectFileScreen {

    public static Parent getScreen() {

        GridPaneBuilder gridPaneBuilder = new GridPaneBuilder(400, 300, 20);

        var selectFileFlow = new TextFlow();
        selectFileFlow.setTextAlignment(TextAlignment.CENTER);
        var select = new Label("Select ");
        select.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        var xlsx = new Label(".xlsx");
        xlsx.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        var file = new Label(" file");
        file.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        selectFileFlow.getChildren().addAll(select, xlsx, file);

        var errorMessage = new Label("");
        errorMessage.setTextFill(Color.valueOf("red"));
        var submit = new Button("Submit");

        submit.setPrefSize(125, 25);
        submit.setDisable(true);

        var cancel = new Button("Cancel");
        cancel.setPrefSize(125, 25);

        var selectFile = new Button("Select file");
        selectFile.setPrefSize(125, 25);

        var fileChooser = new FileChooser();
        selectFile.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            submit.setDisable(false);
        });

        gridPaneBuilder.addComponents(selectFileFlow, selectFile, submit, cancel, errorMessage);
        return gridPaneBuilder.getGridPane();
    }

}
