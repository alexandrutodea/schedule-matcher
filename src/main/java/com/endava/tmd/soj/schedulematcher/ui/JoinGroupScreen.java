package com.endava.tmd.soj.schedulematcher.ui;

import com.endava.tmd.soj.schedulematcher.client.ClientApplication;
import com.endava.tmd.soj.schedulematcher.service.GridPaneBuilder;
import com.endava.tmd.soj.schedulematcher.service.ScreenController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class JoinGroupScreen {

    private JoinGroupScreen() {
        //it makes to instantiate this class
    }

    public static Parent getView() {
        var gridPaneBuilder = new GridPaneBuilder(400, 300, 20);

        TextField groupCodeInputField = new TextField();
        groupCodeInputField.setText(ClientApplication.getGroupCode());

        var join = new Button("Join");
        join.setPrefSize(125, 25);

        var cancel = new Button("Cancel");
        cancel.setMaxSize(125, 25);

        var error = new Label("");
        error.setTextFill(Color.valueOf("red"));

        join.setOnAction(e -> {});
        cancel.setOnAction(e -> ScreenController.displayScreen("main-menu"));

        gridPaneBuilder.addComponents(new Label("Enter group code:"),
                groupCodeInputField, join, cancel, error);

        return gridPaneBuilder.getGridPane();
    }

}
