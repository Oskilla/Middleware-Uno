package com.rmi.server;

import java.util.ArrayList;
import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.rmi.entity.*;
import com.rmi.impl.*;
import com.rmi.intf.*;

public final class RMIServer implements RMIServerInterface{

  private List<JoueurInterface> joueursAttente = new ArrayList<JoueurInterface>();
  private UnoInterface uno;
  private MessageInterface mess = new Message("");

  public RMIServer() {
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
      this.mess = "le joueur " + name + " est entré dans la partie, la partie commence";
    }
    this.mess = "le joueur " + name + " est entré dans la partie";
  }

  public synchronized MessageInterface playCard(String id,CarteInterface c,String couleur){

  }

  public List<CarteInterface> getMyCards(String id){
    
  }

  public boolean GameOver(){
    return this.uno.isGameOver();
  }
}
