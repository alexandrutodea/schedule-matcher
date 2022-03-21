package com.endava.tmd.soj.schedulematcher.server;

import com.endava.tmd.soj.schedulematcher.services.ScheduleGroupManager;

public class ServerApplication {
    private ScheduleGroupManager scheduleGroupManager;

    public ServerApplication(ScheduleGroupManager scheduleGroupManager) {
        this.scheduleGroupManager = scheduleGroupManager;
    }

    public void run() {
        throw new UnsupportedOperationException();
    }
}
