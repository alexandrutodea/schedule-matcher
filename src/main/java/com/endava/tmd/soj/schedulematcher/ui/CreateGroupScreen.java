package com.endava.tmd.soj.schedulematcher.ui;

import com.endava.tmd.soj.schedulematcher.service.GridPaneBuilder;
import com.endava.tmd.soj.schedulematcher.service.ScreenController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class CreateGroupScreen {

    private CreateGroupScreen() {
        //it makes no sense to instantiate this class
    }

    public static Parent getScreen() {
        var gridPaneBuilder = new GridPaneBuilder(400, 300, 20);
        var create = new Label("Create group");
        var flow = new TextFlow();
        flow.setTextAlignment(TextAlignment.CENTER);

        var sizeText = new Label("Size: ");
        var groupSize = new Label("2");

        flow.getChildren().addAll(create, sizeText, groupSize);

        var createGroup = new Button("Create group");
        createGroup.setPrefSize(125, 25);

        var cancel = new Button("Cancel");
        cancel.setPrefSize(125, 25);
        var buttons = new GridPane();

        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(5);
        var plus = new Button("+");
        var minus = new Button("-");
        minus.setDisable(true);

        createGroup.setOnAction(e -> {});

        plus.setOnAction(e -> {
            groupSize.setText(String.valueOf(Integer.parseInt(groupSize.getText()) + 1));
            if (Integer.parseInt(groupSize.getText()) == 10) {
                plus.setDisable(true);
            }
            minus.setDisable(false);
        });

        minus.setOnAction(e -> {
            plus.setDisable(false);
            groupSize.setText(String.valueOf(Integer.parseInt(groupSize.getText()) - 1));
            if (Integer.parseInt(groupSize.getText()) == 2) {
                minus.setDisable(true);
            }
        });

        cancel.setOnAction(e -> ScreenController.displayScreen("main-menu"));

        buttons.add(minus, 0, 0);
        buttons.add(plus, 1, 0);
        gridPaneBuilder.addComponents(create, flow, buttons, createGroup, cancel);
        return gridPaneBuilder.getGridPane();
    }

}
