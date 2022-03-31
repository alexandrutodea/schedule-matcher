package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.client.ClientApplication;
import com.endava.tmd.soj.schedulematcher.ui.*;
import javafx.scene.Parent;

import java.util.HashMap;
import java.util.Map;

public class ScreenController {

    private static final Map<String, Parent> screens = new HashMap<>();

    private ScreenController() {
        //it makes no sense to instantiate this class
    }

    public static void displayScreen(String screenIdentifier) {

        if (screens.containsKey(screenIdentifier)) {
            ClientApplication.getRoot().setCenter(screens.get(screenIdentifier));
        }

        switch (screenIdentifier) {
            case "waiting" -> saveAndDisplay(screenIdentifier, WaitingScreen.getView());
            case "group-created" -> saveAndDisplay(screenIdentifier, GroupCreatedScreen.getView());
            case "join-group" -> saveAndDisplay(screenIdentifier, JoinGroupScreen.getView());
            case "download-template" -> saveAndDisplay(screenIdentifier, DownloadTemplateScreen.getScreen());
            case "combined-schedule" -> saveAndDisplay(screenIdentifier,  CombinedScheduleScreen.getScreen());
            case "main-menu" -> saveAndDisplay(screenIdentifier, MainMenuScreen.getScreen());
            case "create-group" -> saveAndDisplay(screenIdentifier, CreateGroupScreen.getScreen());
            case "select-file" -> saveAndDisplay(screenIdentifier, SelectFileScreen.getScreen());
            default -> throw new IllegalArgumentException("Invalid scene identifier");
        }

    }

    public static void saveAndDisplay(String identifier, Parent screen) {
        screens.put(identifier, screen);
        ClientApplication.getRoot().setCenter(screen);
    }

}
