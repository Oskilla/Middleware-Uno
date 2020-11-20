/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.app;

import com.rmi.client.RMIClient;

public class App {
  public static void main(String[] args) throws Exception {
    //appel du constructeur de la classe RMIClient, lancement du client
    RMIClient rmiClient = new RMIClient();
  }
}
