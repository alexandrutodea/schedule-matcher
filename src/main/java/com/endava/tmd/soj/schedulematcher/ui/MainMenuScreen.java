package com.endava.tmd.soj.schedulematcher.ui;

import com.endava.tmd.soj.schedulematcher.service.GridPaneBuilder;
import javafx.scene.Parent;
import javafx.scene.control.Button;

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
        gridPaneBuilder.addComponents(create, join, exit);
        return gridPaneBuilder.getGridPane();
    }

}
