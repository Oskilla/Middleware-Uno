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
  // attribut representant les messages communs aux joueurs qui attendent que d autres joueurs arrivent, cet attribut doit etre volatile car les joueurs doivent recuperer la valeure la plus a jour
  private volatile MessageInterface mess;

  /**
  * Constructeur de la class RMIServer
  * cree un RMIServer
  */
  public RMIServer() throws RemoteException{
    this.joueursAttente = new ArrayList<JoueurInterface>();
    this.UnoPret = new ConcurrentLinkedQueue<UnoInterface>();
    this.mess = new Message("");
  }

  /**
  * Accesseur de l attribut mess
  * @return l attribut mess
  */
  public MessageInterface getMessageCommun() throws RemoteException{
    return this.mess;
  }

  /**
  * Methode permettant de specifier au uno dont le joueur fait partie qu il est pret, et si tout les joueurs sont prets
  * de retirer le uno de la liste des uno en preparation
  * @param id, l identifiant
  * @return le uno dont le joueur recupera l adresse distante cote client afin de plus passer par la classe RMIServer
  */
  public UnoInterface start(String id) throws RemoteException{
    UnoInterface myUno;
    // on cree une copie du tableau afin d eviter que les elements du tableau changent lorsqu on le parcours
    AbstractCollection<UnoInterface> copy = this.UnoPret;
    for(UnoInterface u : copy){
      if(u.getJoueurByID(id) != null){
        u.joueurPret(id);
        // si les joueurs sont tous pret
        if(u.tousPret()){
          // thread safe
          this.UnoPret.remove(u);
        }
        return u;
      }
    }
    return null;
  }

  /**
  * Methode permettant de rejoindre le lobby des joueurs en attente, si le nombre de joueurs devient 4
  * alors on leur cree un uno
  * cette methode doit etre en synchronized afin qu un seul joueur a la fois soit ajoute au tableau d attente
  * @param name, l identifiant du futur joueur
  */
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
      // Thread safe
      this.UnoPret.add(uno);
      System.out.println("une partie commence");
    }else{
      this.mess.setMessage("le joueur " + name + " est entré dans la partie, en attente d'autres joueurs");
    }
  }
}
