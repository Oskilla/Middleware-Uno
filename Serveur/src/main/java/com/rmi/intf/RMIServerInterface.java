package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface RMIServerInterface extends Remote{
  public MessageInterface getMess() throws RemoteException;
  public JoueurInterface getCourant() throws RemoteException;
  public boolean start() throws RemoteException;
  public boolean joinGame(String name) throws RemoteException;
  public MessageInterface playCard(String id,CarteInterface c,String couleur) throws RemoteException;
  public CarteInterface getLastTalon() throws RemoteException;
  public List<CarteInterface> getMyCards(String id) throws RemoteException;
  public String getCouleurActu() throws RemoteException;
  public boolean GameOver() throws RemoteException;
}
