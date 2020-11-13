package com.rmi.server;

import java.util.ArrayList;
import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.rmi.entity.*;
import com.rmi.intf.*;

public final class RMIServer implements RMIServerInterface{

  private List<JoueurInterface> joueursAttente = new ArrayList<JoueurInterface>();
  private UnoInterface uno;
  private MessageInterface mess;

  public RMIServer() throws RemoteException{
    this.mess = new Message("");
    Thread thread = new Thread(() -> {
      while (true) {
      }
    });
    thread.start();
  }

  public synchronized MessageInterface getMess(){
    return this.mess;
  }

  public JoueurInterface getCourant() throws RemoteException{
    return this.uno.getCourant();
  }

  public synchronized boolean joinGame(String name) throws RemoteException{
    JoueurInterface j = new Joueur(name,null,null);
    joueursAttente.add(j);
    if(joueursAttente.size() == 4){
      this.uno = new Uno(joueursAttente);
      this.uno.InitGame();
      joueursAttente.clear();
      this.mess.setMessage("le joueur " + name + " est entré dans la partie, la partie commence");
      System.out.println("une partie commence");
    }else{
      this.mess.setMessage("le joueur " + name + " est entré dans la partie, en attente d'autres joueurs");
    }
    return true;
  }

  public boolean start() throws RemoteException{
    if(this.uno == null){
      return false;
    }
    return true;
  }

  public synchronized MessageInterface playCard(String id,CarteInterface c,String couleur) throws RemoteException{
    MessageInterface message;
    if(c == null){
      CarteInterface test = this.uno.peutJouer(this.uno.getJoueurByID(id));
      if(test != null){
        return new Message("La carte " + test.affiche() + " peut être jouée");
      }else{
        this.mess.setMessage(id + " ne peut pas jouer, il pioche donc une carte");
        message = new Message(this.uno.JouerCarte(id,this.uno.getCourant().getMain().get(this.uno.getCourant().getMain().size()-1),couleur,true).getMessage());
      }
    }else{
      message = new Message(this.uno.JouerCarte(id,c,couleur,false).getMessage());
    }
    if(message.getMessage().equals("La carte ne peut pas être jouée")){
      return message;
    }
    this.mess.setMessage(message.getMessage());
    return null;
  }

  public synchronized List<CarteInterface> getMyCards(String id) throws RemoteException{
    return this.uno.getJoueurByID(id).getMain();
  }

  public CarteInterface getLastTalon() throws RemoteException{
    return this.uno.getTalon().get(this.uno.getTalon().size()-1);
  }

  public String getCouleurActu() throws RemoteException{
    return this.uno.getCouleurChoisie();
  }

  public boolean GameOver() throws RemoteException{
    return this.uno.isGameOver();
  }
}
