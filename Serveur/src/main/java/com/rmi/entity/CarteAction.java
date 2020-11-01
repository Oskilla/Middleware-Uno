package com.rmi.entity;

import com.rmi.intf.CarteInterface;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class CarteAction extends UnicastRemoteObject implements CarteInterface {

  private String symbole;
  private String couleur;

  public CarteAction(String col, String sym) throws RemoteException{
    this.symbole = sym;
    this.couleur = col;
  }

  public String getCouleur(){
    return this.couleur;
  }

  public String getSymbole(){
    return this.symbole;
  }

  public int getNumero(){
    return -1;
  }

  public boolean equals(CarteInterface c) throws RemoteException{
    if(c.getSymbole().equals(this.symbole) && c.getCouleur().equals(this.couleur)){
      return true;
    }
    return false;
  }

  public String getClassName(){
    return "CarteAction";
  }

  public String affiche(){
    return "couleur: " + this.getCouleur() + " symbole: " + this.symbole;
  }

}
