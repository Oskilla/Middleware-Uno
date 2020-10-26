package com.rmi.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.rmi.impl.ImplMInterface;
import com.rmi.intf.MInterface;

public class RMIServer {

    public void start() {
        // try {
        // Naming.rebind("//localhost/RMIServer", new ImplMInterface());
        // System.err.println("Server is running");
        // } catch (Exception e) {
        // System.err.println("Server exception: " + e.toString());
        // e.printStackTrace();
        // }
        try {

            MInterface mInterface = new ImplMInterface();
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("ImplMInterface_1099", mInterface);

            System.out.println("\n----------------------------------");
            System.out.println("Welcome to the RMI Server !");
            System.out.println("----------------------------------\n");

        } catch (Exception e) {
            System.out.println("An error occured: " + e.toString());
            e.printStackTrace();
        }

    }

}