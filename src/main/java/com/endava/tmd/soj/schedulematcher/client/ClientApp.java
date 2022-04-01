package com.endava.tmd.soj.schedulematcher.client;

import com.endava.tmd.soj.schedulematcher.model.Day;
import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.model.TimeInterval;

public class ClientApp {
    public static void main(String[] args) throws InterruptedException {
        var thread = new CreateGroupThread("127.0.0.1", 5200, 3);
        thread.start();
        thread.join();
        System.out.println(thread.getGroupCode());
        String groupCode = thread.getGroupCode();
        var firstSchedule = new Schedule();
        firstSchedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16));
        firstSchedule.addBusyTimeInterval(Day.TUESDAY, new TimeInterval(17,18));
        var secondSchedule = new Schedule();
        secondSchedule.addBusyTimeInterval(Day.MONDAY, new TimeInterval(15, 16));
        var thirdSchedule = new Schedule();
        thirdSchedule.addBusyTimeInterval(Day.TUESDAY, new TimeInterval(17, 18));
        var joinGroupThread = new JoinGroupThread(groupCode, firstSchedule, "127.0.0.1", 5200);
        var joinGroupThread1 = new JoinGroupThread(groupCode, secondSchedule, "127.0.0.1", 5200);
        var joinGroupThread2 = new JoinGroupThread(groupCode, thirdSchedule, "127.0.0.1", 5200);
        joinGroupThread.start();
        joinGroupThread1.start();
        joinGroupThread2.start();
        joinGroupThread.join();
        joinGroupThread1.join();
        joinGroupThread2.join();
        System.out.println(joinGroupThread.getCombinedSchedule());
        System.out.println(joinGroupThread1.getCombinedSchedule());
        System.out.println(joinGroupThread2.getCombinedSchedule());
    }
}
