package com.endava.tmd.soj.schedulematcher.client;

public class ClientApp {
    public static void main(String[] args) throws InterruptedException {
        var createGroupRunnable = new CreateGroupRunnable("127.0.0.1", 5200, 2);
        var thread = new Thread(createGroupRunnable);
        thread.start();
        thread.join();
        System.out.println(createGroupRunnable.getGroupCode());
    }
}
