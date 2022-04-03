package com.endava.tmd.soj.schedulematcher;

import com.endava.tmd.soj.schedulematcher.client.GraphicalClientApp;
import com.endava.tmd.soj.schedulematcher.server.ServerApplication;

public class ScheduleMatcherApp {
    public static void main(String[] args) {

        if (args.length < 3) {
            throw new IllegalArgumentException("User must provide at least 3 arguments");
        }

        switch (args[0]) {
            case "client":
                GraphicalClientApp.execute(args[1], args[2]);
            case "server":
                ServerApplication.execute(args[1], args[2]);
            default:
                throw new IllegalArgumentException("Invalid argument provided: must be either client or server");
        }

    }
}
