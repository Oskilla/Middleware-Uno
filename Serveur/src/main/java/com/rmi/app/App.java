/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.app;

import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;

import com.rmi.intf.RMIServerInterface;
import com.rmi.server.RMIServer;

public class App {
  public static void main(String[] args) throws Exception {
    try {
      // creation du registry sur le port souhaite
      LocateRegistry.createRegistry(Integer.parseInt(args[0]));
      // on export un nouvel rmi serveur pour les clients
      RMIServerInterface rmiServ = (RMIServerInterface) UnicastRemoteObject.exportObject(new RMIServer(),Integer.parseInt(args[0]));
      // on bind l objet exporte a l adresse du serveur /Server_1099
      Naming.bind("Server", rmiServ);
      System.out.println("\n----------------------------------");
      System.out.println("Welcome to the RMI Server !");
      System.out.println("----------------------------------\n");
    } catch (Exception e) {
      System.out.println("An error occured: " + e.toString());
      e.printStackTrace();
    }
  }
}
