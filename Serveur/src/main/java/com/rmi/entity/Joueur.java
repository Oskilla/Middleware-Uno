package com.rmi.entity;

import java.util.ArrayList;
import java.util.List;

public class Joueur {

  private String identifiant;
  Joueur left;
  Joueur right;
  private List<Carte> main = new ArrayList<Carte>();
  Uno uno;

  public Joueur(String id,Joueur l, Joueur r){
    this.identifiant = id;
    this.left = l;
    this.right = r;
  }

  public String getId(){
    return this.identifiant;
  }

  public Joueur getLeft(){
    return this.left;
  }

  public void setLeft(Joueur j){
    this.left = j;
  }

  public Joueur getRight(){
    return this.right;
  }

  public void setRight(Joueur j){
    this.right = j;
  }

  public List<Carte> getMain(){
    return this.main;
  }

  public Uno getUno(){
    return this.uno;
  }

  public Carte jouer(Carte c){
    if(this.main.contains(c)){
      this.main.remove(c);
      return c;
    }else{
      return null;
    }
  }

  public void piocher(Carte c){
    this.main.add(c);
  }

  public void montreMain(){
    for(Carte c : this.main){
      System.out.println(c.affiche());
    }
  }
}
