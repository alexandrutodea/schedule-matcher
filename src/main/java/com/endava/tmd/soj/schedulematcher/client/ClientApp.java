package com.endava.tmd.soj.schedulematcher.client;

import com.endava.tmd.soj.schedulematcher.model.Day;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.model.TimeInterval;

public class ClientApp {
    public static void main(String[] args) throws InterruptedException {
        var thread = new CreateGroupThread("127.0.0.1", 5200, 2);
        thread.start();
        thread.join();
        System.out.println(thread.getGroupCode());
        String groupCode = thread.getGroupCode();
        var firstSchedule = new Schedule();
        firstSchedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16));
        var secondSchedule = new Schedule();
        secondSchedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16));
        var joinGroupThread = new JoinGroupThread(groupCode, firstSchedule, "127.0.0.1", 5200);
        var joinGroupThread1 = new JoinGroupThread(groupCode, secondSchedule, "127.0.0.1", 5200);
        joinGroupThread.start();
        joinGroupThread1.start();
    }
}
