package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface JoueurInterface extends Remote {
  public String getId() throws RemoteException;
  public JoueurInterface getLeft() throws RemoteException;
  public void setLeft(JoueurInterface j) throws RemoteException;
  public JoueurInterface getRight() throws RemoteException;
  public void setRight(JoueurInterface j) throws RemoteException;
  public List<CarteInterface> getMain() throws RemoteException;
  public CarteInterface jouer(CarteInterface c) throws RemoteException;
  public void piocher(CarteInterface c) throws RemoteException;
  public void montreMain() throws RemoteException;
}
