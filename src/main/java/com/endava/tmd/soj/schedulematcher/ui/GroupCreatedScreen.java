package com.endava.tmd.soj.schedulematcher.ui;

import com.endava.tmd.soj.schedulematcher.client.GraphicalClientApp;
import com.endava.tmd.soj.schedulematcher.service.GridPaneBuilder;
import com.endava.tmd.soj.schedulematcher.service.ScreenController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class GroupCreatedScreen {

    private GroupCreatedScreen() {
        //it makes no sense to instantiate this class
    }

    public static Parent getView() {
        var gridPaneBuilder = new GridPaneBuilder(400, 300, 20);
        var returnToMainMenu = new Button("Return to main menu");

        returnToMainMenu.setOnAction(e -> ScreenController.displayScreen("main-menu"));

        var groupCode = new Label(GraphicalClientApp.getGroupCode());
        groupCode.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));

        gridPaneBuilder.addComponents(
                new Label("Your group has been created."),
                new Label("The group's code is:"),
                groupCode,
                returnToMainMenu);

        return gridPaneBuilder.getGridPane();
    }

}
