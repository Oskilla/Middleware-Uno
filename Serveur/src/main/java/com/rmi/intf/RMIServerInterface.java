/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface RMIServerInterface extends Remote{
  public MessageInterface getMessageCommun() throws RemoteException;
  public UnoInterface start(String id) throws RemoteException;
  public void joinGame(String name) throws RemoteException;
}
