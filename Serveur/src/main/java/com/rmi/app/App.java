package com.rmi.app;

import com.rmi.server.RMIServer;

public class App {
    public static void main(String[] args) throws Exception {
        RMIServer rmiServer = new RMIServer();
        rmiServer.start();
    }
}