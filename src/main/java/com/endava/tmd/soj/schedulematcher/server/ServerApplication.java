package com.endava.tmd.soj.schedulematcher.server;

import com.endava.tmd.soj.schedulematcher.service.ScheduleGroupManager;

public class ServerApplication {
    private ScheduleGroupManager scheduleGroupManager;

    public ServerApplication(ScheduleGroupManager scheduleGroupManager) {
        this.scheduleGroupManager = scheduleGroupManager;
    }

    public void run() {
        throw new UnsupportedOperationException();
    }
}
