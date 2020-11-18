package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface RMIServerInterface extends Remote{
  public MessageInterface getMessageCommun(UnoInterface u) throws RemoteException;
  public JoueurInterface getCourant(UnoInterface u) throws RemoteException;
  public UnoInterface start(String id) throws RemoteException;
  public void joinGame(String name) throws RemoteException;
  public MessageInterface playCard(String id,UnoInterface u,CarteInterface c,String couleur) throws RemoteException;
  public CarteInterface getLastTalon(UnoInterface u) throws RemoteException;
  public List<CarteInterface> getMyCards(String id,UnoInterface u) throws RemoteException;
  public String getCouleurActu(UnoInterface u) throws RemoteException;
  public boolean GameOver(UnoInterface u) throws RemoteException;
}
