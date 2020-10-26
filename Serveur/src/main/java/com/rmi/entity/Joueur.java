package com.rmi.entity;

import com.rmi.intf.*;
import java.util.ArrayList;
import java.util.List;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Joueur extends UnicastRemoteObject implements JoueurInterface{

  private String identifiant;
  JoueurInterface left;
  JoueurInterface right;
  private List<CarteInterface> main = new ArrayList<CarteInterface>();

  public Joueur(String id,JoueurInterface l, JoueurInterface r) throws RemoteException{
    super();
    this.identifiant = id;
    this.left = l;
    this.right = r;
  }

  public String getId(){
    return this.identifiant;
  }

  public JoueurInterface getLeft(){
    return this.left;
  }

  public void setLeft(JoueurInterface j){
    this.left = j;
  }

  public JoueurInterface getRight(){
    return this.right;
  }

  public void setRight(JoueurInterface j){
    this.right = j;
  }

  public List<CarteInterface> getMain(){
    return this.main;
  }

  public CarteInterface jouer(CarteInterface c){
    if(this.main.contains(c)){
      this.main.remove(c);
      return c;
    }else{
      return null;
    }
  }

  public void piocher(CarteInterface c){
    this.main.add(c);
  }

  public void montreMain() throws RemoteException{
    for(CarteInterface c : this.main){
      System.out.println(c.affiche());
    }
  }
}
