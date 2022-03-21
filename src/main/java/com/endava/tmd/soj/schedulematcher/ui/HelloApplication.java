package com.endava.tmd.soj.schedulematcher.ui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage window) throws IOException {
        window.setTitle("Hello World!");
        window.show();
    }

    public static void main(String[] args) {
        launch(HelloApplication.class);
    }
}