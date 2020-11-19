/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */
 
package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

public interface MessageInterface extends Remote {
  public void setMessage(String m) throws RemoteException;
  public String getMessage() throws RemoteException;
}
