package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface RMIServerInterface extends Remote{
  public MessageInterface getMessageCommun(String id,UnoInterface u) throws RemoteException;
  public JoueurInterface getCourant(String id,UnoInterface u) throws RemoteException;
  public UnoInterface start(String id) throws RemoteException;
  public void joinGame(String name) throws RemoteException;
  public MessageInterface playCard(String id,UnoInterface u,CarteInterface c,String couleur) throws RemoteException;
  public CarteInterface getLastTalon(String id,UnoInterface u) throws RemoteException;
  public List<CarteInterface> getMyCards(String id,UnoInterface u) throws RemoteException;
  public String getCouleurActu(String id,UnoInterface u) throws RemoteException;
  public boolean GameOver(String id,UnoInterface u) throws RemoteException;
}
