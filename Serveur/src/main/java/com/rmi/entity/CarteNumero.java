package com.rmi.entity;

import com.rmi.intf.CarteInterface;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class CarteNumero extends UnicastRemoteObject implements CarteInterface {

  private int numero;
  private String couleur;

  public CarteNumero(String col, int num) throws RemoteException{
    this.couleur = col;
    this.numero = num;
  }

  public String getCouleur(){
    return this.couleur;
  }

  public int getNumero(){
    return this.numero;
  }

  public String getSymbole(){
    return "symbol";
  }

  public String affiche(){
    return "Carte: " + this + " couleur: " + this.getCouleur() + " numero: " + this.numero;
  }
}
