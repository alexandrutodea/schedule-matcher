package com.endava.tmd.soj.schedulematcher.client;

import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.service.ScreenController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GraphicalClientApp extends Application {

    private static String groupCode;
    private static BorderPane root;
    private static Schedule schedule;
    private static Schedule combinedSchedule;
    private static String ipAddress;
    private static int port;

    @Override
    public void start(Stage window) {
        Parameters parameters = getParameters();
        ipAddress = parameters.getRaw().get(0);
        port = Integer.parseInt(parameters.getRaw().get(1));
        groupCode = "";
        root = new BorderPane();
        var main = new Scene(root);
        window.setScene(main);
        ScreenController.displayScreen("main-menu");
        window.setTitle("Schedule Matcher");
        window.show();
    }

    public static void main(String[] args) {
        launch(GraphicalClientApp.class, args);
    }

    public static String getGroupCode() {
        return groupCode;
    }

    public static BorderPane getRoot() {
        return root;
    }

    public static void setGroupCode(String groupCode) {
        GraphicalClientApp.groupCode = groupCode;
    }

    public static Schedule getSchedule() {
        return schedule;
    }

    public static void setSchedule(Schedule schedule) {
        GraphicalClientApp.schedule = schedule;
    }

    public static Schedule getCombinedSchedule() {
        return combinedSchedule;
    }

    public static void setCombinedSchedule(Schedule combinedSchedule) {
        GraphicalClientApp.combinedSchedule = combinedSchedule;
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public static int getPort() {
        return port;
    }
}
