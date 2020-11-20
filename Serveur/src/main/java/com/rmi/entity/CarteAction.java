/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.entity;

import com.rmi.intf.CarteInterface;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * Classe representant une CarteAction, une CarteAction est une carte ayant un effet ex: carte(couleur:vert,symbole:interdit)
 */
public class CarteAction extends UnicastRemoteObject implements CarteInterface {
  // attribut representant le symbole (effet) de la carte
  private String symbole;
  // attribut representant la couleur de la carte
  private String couleur;

  /**
  * Constructeur de la class CarteAction
  * cree une CarteAction avec les parametres ci-dessous
  * @param col, la couleur de la carte
  * @param sym, le symbole de la carte
  */
  public CarteAction(String col, String sym) throws RemoteException{
    this.symbole = sym;
    this.couleur = col;
  }

  /**
  * Accesseur de l attribut couleur
  * @return l attribut couleur de la carte
  */
  public String getCouleur(){
    return this.couleur;
  }

  /**
  * Accesseur de l attribut symbole
  * @return l attribut symbole de la carte
  */
  public String getSymbole(){
    return this.symbole;
  }

  /**
  * Accesseur de l attribut numero
  * methode imposee par l interface CarteInterface
  * @return l attribut numero de la carte
  * @deprecated ne sert que pour l objet CarteNumero
  */
  public int getNumero(){
    return -1;
  }

  /**
  * Accesseur au type de la carte
  * @return le type de la carte
  */
  public String getClassName(){
    return "CarteAction";
  }

  /**
  * Methode affichant la carte
  * @return les attributs de la carte au format user-friendly
  */
  public String affiche(){
    return "couleur: " + this.getCouleur() + " symbole: " + this.symbole;
  }

}
