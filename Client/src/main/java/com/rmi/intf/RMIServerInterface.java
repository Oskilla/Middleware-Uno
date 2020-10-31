package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface RMIServerInterface extends Remote{
  public MessageInterface getMess() throws RemoteException;
  public void joinGame(String name) throws RemoteException;
  public MessageInterface playCard(String id,CarteInterface c,String couleur) throws RemoteException;
  public List<CarteInterface> getMyCards(String id) throws RemoteException;
  public boolean GameOver() throws RemoteException;
}
