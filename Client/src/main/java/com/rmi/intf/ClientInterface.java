package com.rmi.intf;

import javafx.event.ActionEvent;

import java.util.List;

import java.io.IOException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote{
  public void init(ActionEvent event,int salle) throws RemoteException;
  public void setPeutJouer() throws RemoteException;
  public void setTalon(CarteInterface c) throws RemoteException;
  public void setLeftJoueur(String name) throws RemoteException;
  public void setCouleurChoisie(String col) throws RemoteException;
  public void setFinPartie(String name) throws IOException;
  public void setRightJoueur(String name) throws RemoteException;
  public void setLastJoueur(String name) throws RemoteException;
  public void setPlayerTurn(String name) throws RemoteException;
  public void setMyHand(List<CarteInterface> c) throws RemoteException;
  public void setJoueurHand(String name,int n) throws RemoteException;
  public void incrementPoint() throws RemoteException;
}
