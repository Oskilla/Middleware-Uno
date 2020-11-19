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

  public MessageInterface getMessageCommun(UnoInterface u) throws RemoteException{
    if(u != null){
      return u.getMess();
    }
    return this.mess;
  }

  public JoueurInterface getCourant(UnoInterface u) throws RemoteException{
    return u.getCourant();
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
      uno.setMess(new Message("le joueur " + name + " est entré dans la partie, la partie commence"));
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

  public MessageInterface playCard(String id,UnoInterface u,CarteInterface c,String couleur) throws RemoteException{
    boolean carte;
    if(c == null){
      CarteInterface test = u.peutJouer(u.getJoueurByID(id));
      if(test != null){
        return new Message("La carte " + test.affiche() + " peut être jouée");
      }else{
        u.JouerCarte(id,c,couleur,false);
        carte = u.JouerCarte(id,u.getCourant().getMain().get(u.getCourant().getMain().size()-1),couleur,true);
      }
    }else{
      carte = u.JouerCarte(id,c,couleur,false);
    }
    if(!carte){
      return new Message("La carte ne peut pas être jouée");
    }
    return null;
  }

  public List<CarteInterface> getMyCards(String id,UnoInterface u) throws RemoteException{
    return u.getJoueurByID(id).getMain();
  }

  public CarteInterface getLastTalon(UnoInterface u) throws RemoteException{
    return u.getTalon().get(u.getTalon().size()-1);
  }

  public String getCouleurActu(UnoInterface u) throws RemoteException{
    return u.getCouleurChoisie();
  }

  public boolean GameOver(UnoInterface u) throws RemoteException{
    return u.isGameOver();
  }
}
