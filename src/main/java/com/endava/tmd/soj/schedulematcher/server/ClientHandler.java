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
    private String groupCode;
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

                Object object = clientIn.readObject();

                if (object instanceof String command) {
                    if (command.contains("create")) {
                        String[] parts = command.split(" ");
                        int size = Integer.parseInt(parts[1]);
                        createGroup(size);
                        break;
                    } else if (command.contains("add")) {
                        String[] parts = command.split(" ");
                        this.groupCode = parts[1];
                        System.out.printf("Group code has been set to %s for client %s:%s\n", groupCode, address, port);
                    } else if (command.equals("exit")) {
                        dropClient(client, address, port);
                        break;
                    } else {
                        System.out.printf("Unknown command %s received from client %s:%s\n", command, address, port);
                    }
                } else if (object instanceof Schedule schedule) {
                    if (groupCode == null) {
                        System.out.printf("Client %s:%s attempted to send schedule without setting a group\n", address, port);
                    }
                    registerSchedule(groupCode, schedule);
                    System.out.println(schedule);
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
    }

    private void createGroup(int size) throws IOException {
        String groupCode = scheduleGroupManager.createGroup(size);
        System.out.printf("Group with code %s has been created by client %s:%s\n", groupCode, address, port);
        clientOut.writeObject(groupCode);
    }
}
