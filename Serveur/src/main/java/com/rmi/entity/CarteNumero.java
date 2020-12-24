/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture Client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.entity;

import com.rmi.intf.CarteInterface;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

// Classe representant une CarteNumero, une CarteNumero est une carte ayant juste un numero
public class CarteNumero extends UnicastRemoteObject implements CarteInterface {
  // attribut representant le numero de la carte
  private int numero;
  // attribut representant la couleur de la carte
  private String couleur;

  /**
  * Constructeur de la class CarteNumero
  * cree une CarteNumero avec les parametres ci-dessous
  * @param col, la couleur de la carte
  * @param num, le symbole de la carte
  */
  public CarteNumero(String col, int num) throws RemoteException{
    this.couleur = col;
    this.numero = num;
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
   * methode imposee par l interface CarteInterface
   * @return l attribut symbole de la carte
   * @deprecated ne sert que pour l objet CarteAction
   */
  public String getSymbole(){
    return "symbole";
  }

  /**
  * Accesseur de l attribut numero
  * @return l attribut numero de la carte
  */
  public int getNumero(){
    return this.numero;
  }

  /**
  * Accesseur au type de la carte
  * @return le type de la carte
  */
  public String getClassName(){
    return "CarteNumero";
  }
}
