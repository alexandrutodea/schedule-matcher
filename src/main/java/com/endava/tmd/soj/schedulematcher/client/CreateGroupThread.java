package com.endava.tmd.soj.schedulematcher.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CreateGroupThread extends Thread {

    private String groupCode;
    private final int size;
    private final int port;
    private final String address;

    public CreateGroupThread(String address, int port, int size) {
        super("Create Group Thread");
        this.address = address;
        this.size = size;
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(address, port)) {
            var serverOut = new ObjectOutputStream(socket.getOutputStream());
            var serverIn = new ObjectInputStream(socket.getInputStream());

            serverOut.writeObject("create " + size);
            Object object = serverIn.readObject();
            if (object instanceof String) {
                this.groupCode = (String) object;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getGroupCode() {
        return groupCode;
    }
}
