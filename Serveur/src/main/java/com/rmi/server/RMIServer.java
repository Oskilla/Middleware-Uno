/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.server;

import java.util.ArrayList;
import java.util.List;

import java.rmi.RemoteException;

import java.util.AbstractCollection;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.rmi.entity.Uno;
import com.rmi.entity.Joueur;
import com.rmi.entity.Message;

import com.rmi.intf.UnoInterface;
import com.rmi.intf.JoueurInterface;
import com.rmi.intf.MessageInterface;
import com.rmi.intf.RMIServerInterface;

/**
 * Classe representant le serveur RMI, Le serveur vas recuperer les joueurs qui s y connectent et vas creer des unos pour chaque quatuors
 * C est ici que les entitees Joueurs & Uno vont etre associees
 */
public class RMIServer implements RMIServerInterface{
  // attribut representant la liste des joueurs en attente, c.a.d ceux qui ne sont pas encore 4
  private List<JoueurInterface> joueursAttente;
  // attribut representant la liste des unos qui sont crees mais dont tout les joueurs n ont pas encore repondu, cette liste est thread safe
  private AbstractCollection<UnoInterface> UnoPret;

  /**
  * Constructeur de la class RMIServer
  * cree un RMIServer
  */
  public RMIServer() throws RemoteException{
    this.joueursAttente = new ArrayList<JoueurInterface>();
    this.UnoPret = new ConcurrentLinkedQueue<UnoInterface>();
  }

  /**
  * Methode permettant de rejoindre le lobby des joueurs en attente, si le nombre de joueurs devient 4
  * alors on leur cree un uno
  * cette methode doit etre en synchronized afin qu un seul joueur a la fois soit ajoute au tableau d attente
  * @param name, l identifiant du futur joueur
  */
  public synchronized JoueurInterface joinGame(String name) throws RemoteException{
    JoueurInterface j = new Joueur(name,null,null);
    joueursAttente.add(j);
    if(joueursAttente.size() == 4){
      this.sendAll(new Message("le joueur " + name + " est entré dans la partie, la partie commence"));
      UnoInterface uno = new Uno(joueursAttente);
      for(JoueurInterface joueurVaJouer : joueursAttente){
        joueurVaJouer.setUno(uno);
      }
      uno.InitGame();
      this.joueursAttente.clear();
      // Thread safe
      this.UnoPret.add(uno);
      System.out.println("une partie commence");
    }else{
      this.sendAll(new Message("le joueur " + name + " est entré dans la partie, en attente d'autres joueurs"));
    }
    return j;
  }

  private void sendAll(MessageInterface m) throws RemoteException{
    for(JoueurInterface j : joueursAttente){
      j.setMess(m);
    }
  }
}
