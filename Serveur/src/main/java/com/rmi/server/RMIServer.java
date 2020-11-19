/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.server;

import java.util.ArrayList;
import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.AbstractCollection;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.rmi.entity.*;
import com.rmi.intf.*;

public class RMIServer implements RMIServerInterface{

  private List<JoueurInterface> joueursAttente;
  private AbstractCollection<UnoInterface> UnoPret;
  private volatile MessageInterface mess;

  public RMIServer() throws RemoteException{
    this.joueursAttente = new ArrayList<JoueurInterface>();
    this.UnoPret = new ConcurrentLinkedQueue<UnoInterface>();
    this.mess = new Message("");
  }

  public MessageInterface getMessageCommun() throws RemoteException{
    return this.mess;
  }

  public UnoInterface start(String id) throws RemoteException{
    UnoInterface myUno;
    AbstractCollection<UnoInterface> copy = this.UnoPret;
    for(UnoInterface u : copy){
      if(u.getJoueurByID(id) != null){
        u.joueurPret(id);
        if(u.tousPret()){
          this.UnoPret.remove(u);
        }
        return u;
      }
    }
    return null;
  }

  public synchronized void joinGame(String name) throws RemoteException{
    JoueurInterface j = new Joueur(name,null,null);
    joueursAttente.add(j);
    if(joueursAttente.size() == 4){
      this.mess.setMessage("le joueur " + name + " est entré dans la partie, la partie commence");
      UnoInterface uno = new Uno(joueursAttente);
      for(JoueurInterface joueurVaJouer : joueursAttente){
        joueurVaJouer.setUno(uno);
      }
      uno.InitGame();
      this.joueursAttente.clear();
      this.UnoPret.add(uno);
      System.out.println("une partie commence");
    }else{
      this.mess.setMessage("le joueur " + name + " est entré dans la partie, en attente d'autres joueurs");
    }
  }
}
