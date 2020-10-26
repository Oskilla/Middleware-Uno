package com.rmi.entity;

public abstract class Carte {

  private String couleur;

  public Carte(String  col) {
    this.couleur = col;
  }

  public String getCouleur(){
    return this.couleur;
  }

  public abstract String getSymbole();

  public abstract int getNumero();

  public abstract String affiche();

}
