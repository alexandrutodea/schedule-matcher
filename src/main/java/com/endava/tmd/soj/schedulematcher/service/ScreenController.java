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

            case "waiting" -> {
                Parent waitingScreen = WaitingScreen.getView();
                saveAndDisplay(screenIdentifier, waitingScreen);
            }

            case "group-created" -> {
                Parent groupCreatedScreen = GroupCreatedScreen.getView();
                saveAndDisplay(screenIdentifier, groupCreatedScreen);
            }

            case "join-group" -> {
                var joinGroupScreen = JoinGroupScreen.getView();
                saveAndDisplay(screenIdentifier, joinGroupScreen);
            }

            case "download-template" -> {
                var downloadTemplateScreen = DownloadTemplateScreen.getScreen();
                saveAndDisplay(screenIdentifier, downloadTemplateScreen);
            }

            case "combined-schedule" -> {
                var combinedScheduleScreen = CombinedScheduleScreen.getScreen();
                saveAndDisplay(screenIdentifier, combinedScheduleScreen);
            }

            case "main-menu" -> {
                var mainMenuScreen = MainMenuScreen.getScreen();
                saveAndDisplay(screenIdentifier, mainMenuScreen);

            }

            case "create-group" -> {
                var createGroupScreen = CreateGroupScreen.getScreen();
                saveAndDisplay(screenIdentifier, createGroupScreen);
            }

            case "select-file" -> {
                var selectFileScreen = SelectFileScreen.getScreen();
                saveAndDisplay(screenIdentifier, selectFileScreen);
            }

            default -> throw new IllegalArgumentException("Invalid scene identifier");
        }

    }

    public static void saveAndDisplay(String identifier, Parent screen) {
        screens.put(identifier, screen);
        ClientApplication.getRoot().setCenter(screen);
    }

}
