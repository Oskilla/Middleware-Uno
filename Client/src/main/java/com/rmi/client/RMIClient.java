package com.rmi.client;

import java.rmi.Naming;

import com.rmi.intf.MInterface;

public class RMIClient {

    MInterface mInterface;

    public RMIClient() {

        try {

            mInterface = (MInterface) Naming.lookup("rmi://localhost/ImplMInterface_1099");
            mInterface.printMsg();

        } catch (Exception e) {
            System.out.println("A problem occured with server: " + e.toString());
            e.printStackTrace();
        }
    }
}