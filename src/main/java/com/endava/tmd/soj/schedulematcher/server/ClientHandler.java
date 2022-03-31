package com.endava.tmd.soj.schedulematcher.server;

import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.service.ScheduleGroupManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket client;
    private ScheduleGroupManager scheduleGroupManager;
    private ObjectOutputStream clientOut;
    private ObjectInputStream clientIn;
    private InetAddress address;
    private int port;

    public ClientHandler(Socket socket, ScheduleGroupManager scheduleGroupManager) {
        this.client = socket;
        this.scheduleGroupManager = scheduleGroupManager;
    }

    @Override
    public void run() {
        try {
            this.clientOut = new ObjectOutputStream(client.getOutputStream());
            this.clientIn = new ObjectInputStream(client.getInputStream());
            this.address = client.getInetAddress();
            this.port = client.getPort();

            while (true) {
                String line = (String) clientIn.readObject();

                if (line.contains("create")) {

                    String[] parts = line.split(" ");
                    int size = Integer.parseInt(parts[1]);
                    createGroup(size);
                    break;

                } else if (line.contains("add")) {

                    String[] parts = line.split(" ");
                    String groupCode = parts[1];
                    Schedule schedule = (Schedule) clientIn.readObject();
                    registerSchedule(groupCode, schedule);

                } else if (line.equals("exit")) {

                    dropClient(client, address, port);
                    break;

                } else {

                    System.out.printf("Unknown command %s received from client %s:%s\n", line, address, port);

                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void dropClient(Socket client, InetAddress address, int port) throws IOException {
        System.out.printf("Dropping client %s:%s\n", address, port);
        client.close();
    }

    private void registerSchedule(String groupCode, Schedule schedule) throws IOException {
        scheduleGroupManager.registerMemberSchedule(groupCode, schedule);
        System.out.printf("Registered schedule from client %s:%s\n", address, port);

        if (scheduleGroupManager.hasMaxSizeBeenReached(groupCode)) {
            clientOut.writeObject("combined schedule ready");
            clientOut.writeObject(scheduleGroupManager.getCombinedSchedule(groupCode));
        } else {
            clientOut.writeObject("combined schedule not ready");
        }
    }

    private void createGroup(int size) throws IOException {
        String groupCode = scheduleGroupManager.createGroup(size);
        System.out.println("Group with code " + groupCode + " has been created");
        clientOut.writeObject(groupCode);
    }
}
