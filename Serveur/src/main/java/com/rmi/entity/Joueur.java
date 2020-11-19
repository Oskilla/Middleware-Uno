/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.entity;

import com.rmi.intf.*;
import java.util.ArrayList;
import java.util.List;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Joueur extends UnicastRemoteObject implements JoueurInterface{

  private String identifiant;
  private JoueurInterface left;
  private JoueurInterface right;
  private List<CarteInterface> main = new ArrayList<CarteInterface>();
  private UnoInterface myUno;

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

  public CarteInterface jouer(CarteInterface carte) throws RemoteException{
    for(CarteInterface c : this.main){
      if(c.equals(carte)){
        this.main.remove(c);
        return c;
      }
    }
    return null;
  }

  public boolean contient(CarteInterface carte) throws RemoteException{
    for(CarteInterface c : this.main){
      if(c.equals(carte)){
        return true;
      }
    }
    return false;
  }

  public void piocher(CarteInterface c){
    this.main.add(c);
  }

  public void montreMain() throws RemoteException{
    for(CarteInterface c : this.main){
      System.out.println(c.affiche());
    }
  }

  public UnoInterface getUno() throws RemoteException{
    return this.myUno;
  }

  public void setUno(UnoInterface u) throws RemoteException{
    this.myUno = u;
  }
}
