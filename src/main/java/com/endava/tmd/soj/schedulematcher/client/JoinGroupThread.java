package com.endava.tmd.soj.schedulematcher.client;

import com.endava.tmd.soj.schedulematcher.model.Schedule;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class JoinGroupThread extends Thread {
    private String groupCode;
    private Schedule schedule;
    private Schedule combinedSchedule;
    private String ipAddress;
    private int port;

    public JoinGroupThread(String groupCode, Schedule schedule, String ipAddress, int port) {
        super("Join Group Thread");
        this.groupCode = groupCode;
        this.schedule = schedule;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            var socket = new Socket(InetAddress.getByName(ipAddress), port);
            var serverOut = new ObjectOutputStream(socket.getOutputStream());
            var serverIn = new ObjectInputStream(socket.getInputStream());
            serverOut.writeObject("add " + groupCode);
            serverOut.writeObject(schedule);
            serverOut.writeObject("exit");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Schedule getCombinedSchedule() {
        return combinedSchedule;
    }
}
