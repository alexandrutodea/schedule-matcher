package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.Schedule;

import java.io.InputStream;

public interface ScheduleLoader {
    Schedule loadSchedule(InputStream inputStream);
}
