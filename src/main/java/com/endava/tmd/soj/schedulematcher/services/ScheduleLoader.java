package com.endava.tmd.soj.schedulematcher.services;

import java.io.InputStream;

public interface ScheduleLoader {
    ScheduleGroupManager loadSchedule(InputStream inputStream);
}
