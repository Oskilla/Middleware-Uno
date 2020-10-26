package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MInterface extends Remote {
    void printMsg() throws RemoteException;
}