/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

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
  public boolean contient(CarteInterface c) throws RemoteException;
  public void montreMain() throws RemoteException;
  public UnoInterface getUno() throws RemoteException;
  public void setUno(UnoInterface u) throws RemoteException;
  public MessageInterface getMess() throws RemoteException;
  public void setMess(MessageInterface m) throws RemoteException;
  public void incrementPoint(int n) throws RemoteException;
  public int getPoint() throws RemoteException;
}
