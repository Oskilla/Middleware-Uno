package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote{
  public UnoInterface getUno() throws RemoteException;
  public String joinGame(String name) throws RemoteException;
}
