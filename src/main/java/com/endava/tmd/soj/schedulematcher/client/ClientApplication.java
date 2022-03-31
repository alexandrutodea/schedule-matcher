package com.endava.tmd.soj.schedulematcher.client;

import com.endava.tmd.soj.schedulematcher.service.ScreenController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {

    private static String groupCode;
    private static BorderPane root;

    @Override
    public void start(Stage window) throws IOException {
        groupCode = "";
        root = new BorderPane();
        var main = new Scene(root);
        window.setScene(main);
        ScreenController.displayScreen("main-menu");
        window.setTitle("Schedule Matcher");
        window.show();
    }

    public static void main(String[] args) {
        launch(com.endava.tmd.soj.schedulematcher.client.ClientApplication.class);
    }

    public static String getGroupCode() {
        return groupCode;
    }

    public static BorderPane getRoot() {
        return root;
    }
}
