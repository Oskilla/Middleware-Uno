package com.rmi.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

import com.rmi.entity.*;
import com.rmi.impl.*;
import com.rmi.intf.*;

public class RMIServer {
  public void start() {
    try {
      JoueurInterface j = new Joueur("Damien",null,null);
      JoueurInterface j2 = new Joueur("Damien2",null,null);
      JoueurInterface j3 = new Joueur("Damien3",null,null);
      JoueurInterface j4 = new Joueur("Damien4",null,null);

      List<JoueurInterface> joueurs = new ArrayList<JoueurInterface>();

      joueurs.add(j);
      joueurs.add(j2);
      joueurs.add(j3);
      joueurs.add(j4);

      UnoInterface uno = new Uno(joueurs);
      uno.InitGame();
      LocateRegistry.createRegistry(1099);
      Naming.bind("ImplMInterface_1099", uno);

      System.out.println("\n----------------------------------");
      System.out.println("Welcome to the RMI Server !");
      System.out.println("----------------------------------\n");
    } catch (Exception e) {
      System.out.println("An error occured: " + e.toString());
      e.printStackTrace();
    }
  }
}
