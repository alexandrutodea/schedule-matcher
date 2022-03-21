package com.endava.tmd.soj.schedulematcher.client;

import com.endava.tmd.soj.schedulematcher.services.ScheduleLoader;
import com.endava.tmd.soj.schedulematcher.services.ScheduleWriter;
import com.endava.tmd.soj.schedulematcher.services.TemplateFileDownloader;

public class ClientApplication {
    private ScheduleLoader scheduleLoader;
    private ScheduleWriter scheduleWriter;
    private TemplateFileDownloader templateFileDownloader;

    public ClientApplication(ScheduleLoader scheduleLoader, ScheduleWriter scheduleWriter, TemplateFileDownloader templateFileDownloader) {
        this.scheduleLoader = scheduleLoader;
        this.scheduleWriter = scheduleWriter;
        this.templateFileDownloader = templateFileDownloader;
    }

    public void run() {
        throw new UnsupportedOperationException();
    }
}
