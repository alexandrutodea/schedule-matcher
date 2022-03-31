package com.endava.tmd.soj.schedulematcher.ui;

import com.endava.tmd.soj.schedulematcher.service.GridPaneBuilder;
import com.endava.tmd.soj.schedulematcher.service.ScreenController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuScreen {

    private MainMenuScreen() {
        //it makes no instantiate this class
    }

    public static Parent getScreen() {
        var gridPaneBuilder = new GridPaneBuilder(400, 300, 20);

        var create = new Button("Create group");
        create.setPrefSize(125, 25);
        var join = new Button("Join group");
        join.setPrefSize(125, 25);
        var exit = new Button("Exit");
        exit.setPrefSize(125, 25);

        create.setOnAction(e -> ScreenController.displayScreen("create-group"));
        join.setOnAction(e -> ScreenController.displayScreen("join-group"));
        exit.setOnAction(e -> ((Stage) exit.getScene().getWindow()).close());

        gridPaneBuilder.addComponents(create, join, exit);
        return gridPaneBuilder.getGridPane();
    }

}
