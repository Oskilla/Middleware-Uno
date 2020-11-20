/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.entity;

import com.rmi.intf.MessageInterface;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * Classe representant un Message
 */
public class Message extends UnicastRemoteObject implements MessageInterface{
  // attribut representant le message
  private String message;

  /**
  * Constructeur de la class Message
  * cree un Message avec le parametre ci-dessous
  * @param m, le message
  */
  public Message(String m) throws RemoteException{
    this.message = m;
  }

  /**
  * Setter de l attribut message
  * @param Mess, le message a changer
  */
  public void setMessage(String Mess){
    this.message = Mess;
  }

  /**
  * Acceseur de l attribut message
  * @return le message
  */
  public String getMessage(){
    return this.message;
  }
}
