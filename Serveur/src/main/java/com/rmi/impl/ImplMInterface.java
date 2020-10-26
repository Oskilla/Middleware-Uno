package com.rmi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.rmi.intf.MInterface;

public class ImplMInterface extends UnicastRemoteObject implements MInterface {

    private static final long serialVersionUID = 1L;

    public ImplMInterface() throws RemoteException {
        super();
    }

    @Override
    public void printMsg() throws RemoteException {
        System.out.println("Received client request :)");
    }
}