package com.rmi.entity;

public class CarteNumero extends Carte {

  private int numero;

  public CarteNumero(String col, int num) {
    super(col);
    this.numero = num;
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
