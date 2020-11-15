package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface RMIServerInterface extends Remote{
  public UnoInterface getJoueurDansUno(String id) throws RemoteException;
  public MessageInterface getMessageCommun(String id) throws RemoteException;
  public JoueurInterface getCourant(String id) throws RemoteException;
  public boolean start(String id) throws RemoteException;
  public void joinGame(String name) throws RemoteException;
  public MessageInterface playCard(String id,CarteInterface c,String couleur) throws RemoteException;
  public CarteInterface getLastTalon(String id) throws RemoteException;
  public List<CarteInterface> getMyCards(String id) throws RemoteException;
  public String getCouleurActu(String id) throws RemoteException;
  public boolean GameOver(String id) throws RemoteException;
}
