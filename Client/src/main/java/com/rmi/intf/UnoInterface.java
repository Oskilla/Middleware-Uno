/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.intf;

import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UnoInterface extends Remote {
  public void InitGame() throws RemoteException;
  public boolean JouerCarte(String id,CarteInterface c,String col,boolean aPioche) throws RemoteException;
  public CarteInterface peutJouer(JoueurInterface j) throws RemoteException;
  public JoueurInterface getJoueurByID(String id) throws RemoteException;
  public List<CarteInterface> getTalon() throws RemoteException;
  public boolean isGameOver() throws RemoteException;
  public JoueurInterface getCourant() throws RemoteException;
  public String getCouleurChoisie() throws RemoteException;
  public MessageInterface getMess() throws RemoteException;
  public void joueurPret(String id) throws RemoteException;
  public boolean tousPret() throws RemoteException;
}
