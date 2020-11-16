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
  private MessageInterface mess;

  public RMIServer() throws RemoteException{
    this.joueursAttente = new ArrayList<JoueurInterface>();
    this.UnoPret = new ConcurrentLinkedQueue<UnoInterface>();
    this.mess = new Message("");
  }

  public synchronized MessageInterface getMessageCommun(String id,UnoInterface u) throws RemoteException{
    if(u != null){
      return u.getMess();
    }
    return this.mess;
  }

  public synchronized JoueurInterface getCourant(String id, UnoInterface u) throws RemoteException{
    return u.getCourant();
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

  public synchronized MessageInterface playCard(String id,UnoInterface u,CarteInterface c,String couleur) throws RemoteException{
    MessageInterface message;
    if(c == null){
      CarteInterface test = u.peutJouer(u.getJoueurByID(id));
      if(test != null){
        return new Message("La carte " + test.affiche() + " peut être jouée");
      }else{
        u.JouerCarte(id,c,couleur,false);
        message = new Message(u.JouerCarte(id,u.getCourant().getMain().get(u.getCourant().getMain().size()-1),couleur,true).getMessage());
      }
    }else{
      message = new Message(u.JouerCarte(id,c,couleur,false).getMessage());
    }
    if(message.getMessage().equals("La carte ne peut pas être jouée")){
      return message;
    }
    if(u.isGameOver()){
      this.mess.setMessage(id + " a gagné");
    }
    return null;
  }

  public synchronized List<CarteInterface> getMyCards(String id,UnoInterface u) throws RemoteException{
    return u.getJoueurByID(id).getMain();
  }

  public synchronized CarteInterface getLastTalon(String id,UnoInterface u) throws RemoteException{
    return u.getTalon().get(u.getTalon().size()-1);
  }

  public synchronized String getCouleurActu(String id,UnoInterface u) throws RemoteException{
    return u.getCouleurChoisie();
  }

  public boolean GameOver(String id,UnoInterface u) throws RemoteException{
    return u.isGameOver();
  }
}
