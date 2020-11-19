/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */
 
package com.rmi.app;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;

import com.rmi.intf.*;
import com.rmi.server.RMIServer;

public class App {
  public static void main(String[] args) throws Exception {
    try {
      LocateRegistry.createRegistry(1099);
      RMIServerInterface rmiServ = (RMIServerInterface) UnicastRemoteObject
        .exportObject(new RMIServer(), 1099);
      Naming.bind("Server_1099", rmiServ);
      System.out.println("\n----------------------------------");
      System.out.println("Welcome to the RMI Server !");
      System.out.println("----------------------------------\n");
    } catch (Exception e) {
      System.out.println("An error occured: " + e.toString());
      e.printStackTrace();
    }
  }
}
