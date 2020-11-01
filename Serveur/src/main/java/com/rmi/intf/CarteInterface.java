package com.rmi.intf;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CarteInterface extends Remote{
  public String getCouleur() throws RemoteException;
  public abstract String getSymbole() throws RemoteException;
  public abstract int getNumero() throws RemoteException;
  public boolean equals(CarteInterface c) throws RemoteException;
  public abstract String affiche() throws RemoteException;
  public abstract String getClassName() throws RemoteException;

}
