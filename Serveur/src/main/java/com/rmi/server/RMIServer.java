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
  private volatile MessageInterface mess;

  public RMIServer() throws RemoteException{
    this.mess = new Message("");
    Thread thread = new Thread(() -> {
      while (true) {
      }
    });
    thread.start();
  }

  public MessageInterface getMess(){
    return this.mess;
  }

  public synchronized void joinGame(String name) throws RemoteException{
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
  }

  public synchronized MessageInterface playCard(String id,CarteInterface c,String couleur) throws RemoteException{
    if(this.uno.JouerCarte(id,c,couleur)){
      this.mess.setMessage(id + " a joué la carte " + c.affiche());
    }else{
      return new Message("La carte ne peut pas être jouée");
    }
    return null;
  }

  public List<CarteInterface> getMyCards(String id) throws RemoteException{
    return this.uno.getJoueurByID(id).getMain();
  }

  public boolean GameOver() throws RemoteException{
    return this.uno.isGameOver();
  }
}
