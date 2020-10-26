package com.rmi.intf;

import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UnoInterface extends Remote {
  public void InitGame() throws RemoteException;
  public boolean JouerCarte(JoueurInterface J,CarteInterface c,String col) throws RemoteException;
  public void CarteJouer(JoueurInterface j,CarteInterface c) throws RemoteException;
  public CarteInterface peutJouer(JoueurInterface j) throws RemoteException;
  public boolean peutJouer(CarteInterface c, JoueurInterface j) throws RemoteException;
  public void talonIntoPioche() throws RemoteException;
  public List<JoueurInterface> getJoueurs() throws RemoteException;
  public JoueurInterface getJoueur(int i) throws RemoteException;
  public List<CarteInterface> getTalon() throws RemoteException;
  public List<CarteInterface> getPioche() throws RemoteException;
  public boolean isGameOver() throws RemoteException;
  public void setGameOver() throws RemoteException;
  public JoueurInterface getCourant() throws RemoteException;
  public String getCouleurChoisie() throws RemoteException;
  public void setCourant(JoueurInterface newCourant) throws RemoteException;
  public boolean getSens() throws RemoteException;
  public void setSens() throws RemoteException;
  public void melangerList(List<CarteInterface> aMelanger) throws RemoteException;
}
