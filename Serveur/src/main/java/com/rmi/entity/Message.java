/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */
 
package com.rmi.entity;

import com.rmi.intf.MessageInterface;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Message extends UnicastRemoteObject implements MessageInterface{
  private String message;

  public Message(String m) throws RemoteException{
    this.message = m;
  }

  public void setMessage(String Mess){
    this.message = Mess;
  }

  public String getMessage(){
    return this.message;
  }
}
