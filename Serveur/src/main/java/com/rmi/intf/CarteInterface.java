/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CarteInterface extends Remote{
  public String getCouleur() throws RemoteException;
  public abstract String getSymbole() throws RemoteException;
  public abstract int getNumero() throws RemoteException;
  public abstract String getClassName() throws RemoteException;

}
