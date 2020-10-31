package com.rmi.server;

import java.util.ArrayList;
import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.rmi.entity.*;
import com.rmi.impl.*;
import com.rmi.intf.*;

public class RMIServer implements RMIServerInterface{

  private List<JoueurInterface> joueursAttente = new ArrayList<JoueurInterface>();
  private UnoInterface uno;

  public RMIServer() {
    Thread thread = new Thread(() -> {
      while (true) {
      }
    });
    thread.start();
  }

  public UnoInterface getUno(){
    return this.uno;
  }

  public synchronized String joinGame(String name) throws RemoteException{
    JoueurInterface j = new Joueur(name,null,null);
    joueursAttente.add(j);
    if(joueursAttente.size() == 4){
      this.uno = new Uno(joueursAttente);
      this.uno.InitGame();
      joueursAttente.clear();
      return "le joueur " + name + " est entré dans la partie, la partie commence";
    }
    return "le joueur " + name + " est entré dans la partie";
  }
}
