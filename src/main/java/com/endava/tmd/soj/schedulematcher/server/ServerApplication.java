package com.endava.tmd.soj.schedulematcher.server;

import com.endava.tmd.soj.schedulematcher.service.ScheduleGroupManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerApplication {

    protected static final Map<String, List<ClientHandler>> handlers = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        var scheduleGroupManager = new ScheduleGroupManager();

        try {
            var address = InetAddress.getByName(args[0]);
            int port = Integer.parseInt(args[1]);
            int backlog = 50;
            var serverSocket = new ServerSocket(port, backlog, address);
            serverSocket.setReuseAddress(true);
            System.out.println("Started Server");

            while (true) {
                System.out.println("Accepting a new client...");
                var client = serverSocket.accept();
                Thread thread = new Thread(new ClientHandler(client, scheduleGroupManager));
                thread.start();
                System.out.printf("Accepted connection from %s:%s\n", client.getInetAddress(), client.getPort());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
