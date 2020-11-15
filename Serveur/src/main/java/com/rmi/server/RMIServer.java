package com.rmi.server;

import java.util.AbstractCollection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.ArrayList;
import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.rmi.entity.*;
import com.rmi.intf.*;

public class RMIServer implements RMIServerInterface{

  private List<JoueurInterface> joueursAttente;
  private AbstractCollection<UnoInterface> UnoEnCours;
  private volatile MessageInterface mess;

  public RMIServer() throws RemoteException{
    this.joueursAttente = new ArrayList<JoueurInterface>();
    this.UnoEnCours = new ConcurrentLinkedQueue<UnoInterface>();
    this.mess = new Message("");
  }

  public UnoInterface getJoueurDansUno(String id) throws RemoteException{
    for(UnoInterface unos : this.UnoEnCours){
      if(unos.getJoueurByID(id) != null){
        return unos;
      }
    }
    return null;
  }

  public synchronized MessageInterface getMessageCommun(String id) throws RemoteException{
    UnoInterface u = getJoueurDansUno(id);
    if(u != null){
      return u.getMess();
    }
    return this.mess;
  }

  public synchronized JoueurInterface getCourant(String id) throws RemoteException{
    UnoInterface u = getJoueurDansUno(id);
    return u.getCourant();
  }

  public synchronized void joinGame(String name) throws RemoteException{
    JoueurInterface j = new Joueur(name,null,null);
    joueursAttente.add(j);
    if(joueursAttente.size() == 4){
      this.mess.setMessage("le joueur " + name + " est entré dans la partie, la partie commence");
      UnoInterface uno = new Uno(joueursAttente);
      uno.InitGame();
      this.UnoEnCours.add(uno);
      this.joueursAttente.clear();
      System.out.println("une partie commence");
    }else{
      this.mess.setMessage("le joueur " + name + " est entré dans la partie, en attente d'autres joueurs");
    }
  }

  public boolean start(String id) throws RemoteException{
    UnoInterface u = getJoueurDansUno(id);
    if(u != null){
      return true;
    }
    return false;
  }

  public synchronized MessageInterface playCard(String id,CarteInterface c,String couleur) throws RemoteException{
    UnoInterface u = getJoueurDansUno(id);
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
      this.UnoEnCours.remove(u);
      this.mess.setMessage(id + " a gagné");
    }
    return null;
  }

  public synchronized List<CarteInterface> getMyCards(String id) throws RemoteException{
    UnoInterface u = getJoueurDansUno(id);
    return u.getJoueurByID(id).getMain();
  }

  public synchronized CarteInterface getLastTalon(String id) throws RemoteException{
    UnoInterface u = getJoueurDansUno(id);
    return u.getTalon().get(u.getTalon().size()-1);
  }

  public synchronized String getCouleurActu(String id) throws RemoteException{
    UnoInterface u = getJoueurDansUno(id);
    return u.getCouleurChoisie();
  }

  public boolean GameOver(String id) throws RemoteException{
    UnoInterface u = getJoueurDansUno(id);
    return u.isGameOver();
  }
}
