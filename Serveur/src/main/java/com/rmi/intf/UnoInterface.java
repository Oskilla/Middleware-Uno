/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.intf;

import java.io.IOException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UnoInterface extends Remote {
  public boolean JouerCarte(String id,CarteInterface c,String col,boolean aPioche) throws IOException;
  public CarteInterface peutJouer(JoueurInterface j) throws RemoteException;
  public void joueurPret(String id) throws RemoteException;
}
