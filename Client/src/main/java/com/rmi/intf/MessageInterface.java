package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface MessageInterface extends Remote {
  public void setMessage(String m) throws RemoteException;
  public String getMessage() throws RemoteException;
}
