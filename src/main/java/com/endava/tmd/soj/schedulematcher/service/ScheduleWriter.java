package com.endava.tmd.soj.schedulematcher.service;

import com.endava.tmd.soj.schedulematcher.model.Schedule;

import java.io.OutputStream;

public interface ScheduleWriter {
    void writeSchedule(OutputStream outputStream, Schedule schedule);
}
