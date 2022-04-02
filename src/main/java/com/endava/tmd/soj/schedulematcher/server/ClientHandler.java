package com.endava.tmd.soj.schedulematcher.server;

import com.endava.tmd.soj.schedulematcher.model.Schedule;
import com.endava.tmd.soj.schedulematcher.service.ScheduleGroupManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientHandler implements Runnable {

    private final Socket client;
    private final ScheduleGroupManager scheduleGroupManager;
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
                        ServerApplication.handlers.putIfAbsent(groupCode, new ArrayList<>());
                        List<ClientHandler> groupHandlers = ServerApplication.handlers.get(groupCode);
                        groupHandlers.add(this);

                    } else if (command.equals("exit")) {

                        dropClient(client, address, port);
                        break;

                    } else {

                        System.out.printf("Unknown command %s received from client %s:%s\n", command, address, port);

                    }
                } else if (object instanceof Schedule schedule) {

                    if (groupCode == null) {
                        System.out.printf("Client %s:%s attempted to send schedule without setting a group\n",
                                address, port);
                    }

                    registerSchedule(groupCode, schedule);

                    if (scheduleGroupManager.hasMaxSizeBeenReached(groupCode)) {
                        List<ClientHandler> handlers = ServerApplication.handlers.get(groupCode);
                        synchronized (handlers) {
                            Iterator<ClientHandler> iterator = handlers.iterator();
                            while (iterator.hasNext()) {
                                ClientHandler clientHandler = iterator.next();
                                try {
                                    synchronized (clientHandler.clientOut) {
                                        Schedule combinedSchedule = scheduleGroupManager.getCombinedSchedule(groupCode);
                                        System.out.printf("Sent combined schedule to client %s:%s\n",
                                                clientHandler.address,
                                                clientHandler.port);
                                        clientHandler.clientOut.writeObject(combinedSchedule);
                                        iterator.remove();
                                    }
                                } catch (IOException exception) {
                                    exception.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private synchronized void dropClient(Socket client, InetAddress address, int port) throws IOException {
        System.out.printf("Dropping client %s:%s\n", address, port);
        client.close();
    }

    private synchronized void registerSchedule(String groupCode, Schedule schedule) throws IOException {
        scheduleGroupManager.registerMemberSchedule(groupCode, schedule);
        System.out.printf("Registered schedule from client %s:%s\n", address, port);
    }

    private synchronized void createGroup(int size) throws IOException {
        String groupCode = scheduleGroupManager.createGroup(size);
        System.out.printf("Group with code %s has and size %s been created by client %s:%s\n", groupCode, size, address, port);
        clientOut.writeObject(groupCode);
    }
}