/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture Client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.server;

import java.util.ArrayList;
import java.util.List;

import java.rmi.RemoteException;

import com.rmi.entity.Uno;
import com.rmi.entity.Joueur;

import com.rmi.intf.*;

/**
 * Classe representant le serveur RMI, Le serveur va recuperer les joueurs qui s y connectent et va creer des unos pour chaque quatuors
 * C est ici que les entitees Joueurs & Uno vont etre associees
 */
public class RMIServer implements RMIServerInterface{
  // attribut representant la liste des lobbys, cette liste n est pas thread safe mais jamais personne n effectue d operations en parallele
  private List<List<JoueurInterface>> lobbys;

  /**
  * Constructeur de la class RMIServer
  * cree un RMIServer
  */
  public RMIServer() throws RemoteException{
    this.lobbys = new ArrayList<List<JoueurInterface>>();
    this.lobbys.add(new ArrayList<JoueurInterface>());
  }

  /**
  * Methode permettant de rejoindre le lobby des joueurs en attente, si le nombre de joueurs devient 4
  * alors on leur cree un uno
  * cette methode doit etre en synchronized afin qu un seul joueur a la fois soit ajoute au tableau d attente
  * @param name, l identifiant du futur joueur
  */
  public synchronized JoueurInterface joinGame(String name, int numlobby, ClientInterface cI) throws RemoteException{
    JoueurInterface j = null;
    // si le joueur souhaite creer un lobby
    if(numlobby == -1){
      ArrayList<JoueurInterface> nouveauLobby = new ArrayList<JoueurInterface>();
      j = new Joueur(name,null,null,cI);
      nouveauLobby.add(j);
      // thread safe
      this.lobbys.add(nouveauLobby);
    }else{
      // on verifie que son pseudo n est pas deja present dans le lobby
      if(checkName(name,numlobby)){
        // on creer une copie locale
        List<List<JoueurInterface>> temp = this.lobbys;
        // switch obligatoire pour voir l ajout des personnes graphiquement
        switch(temp.get(numlobby).size()) {
          case 0:
            j = new Joueur(name,null,null,cI);
            break;
          case 1:
            j = new Joueur(name,temp.get(numlobby).get(0),null,cI);
            j.getClient().setLeftJoueur(temp.get(numlobby).get(0).getId());
            temp.get(numlobby).get(0).setRight(j);
            temp.get(numlobby).get(0).getClient().setRightJoueur(j.getId());
            break;
          case 2:
            j = new Joueur(name,temp.get(numlobby).get(1),null,cI);
            temp.get(numlobby).get(1).setRight(j);
            j.getClient().setLeftJoueur(temp.get(numlobby).get(1).getId());
            j.getClient().setLastJoueur(temp.get(numlobby).get(0).getId());
            temp.get(numlobby).get(1).getClient().setRightJoueur(j.getId());
            temp.get(numlobby).get(0).getClient().setLastJoueur(j.getId());
            break;
          case 3:
            j = new Joueur(name,temp.get(numlobby).get(2),temp.get(numlobby).get(0),cI);
            temp.get(numlobby).get(2).setRight(j);
            temp.get(numlobby).get(0).setLeft(j);
            j.getClient().setLeftJoueur(temp.get(numlobby).get(2).getId());
            j.getClient().setRightJoueur(temp.get(numlobby).get(0).getId());
            j.getClient().setLastJoueur(temp.get(numlobby).get(1).getId());
            temp.get(numlobby).get(2).getClient().setRightJoueur(j.getId());
            temp.get(numlobby).get(1).getClient().setLastJoueur(j.getId());
            temp.get(numlobby).get(0).getClient().setLeftJoueur(j.getId());
            break;
        }
        temp.get(numlobby).add(j);
        // si le lobby est plein
        if(temp.get(numlobby).size() == 4){
          // creation du uno
          UnoInterface uno = new Uno(temp.get(numlobby));
          for(JoueurInterface joueurVaJouer : temp.get(numlobby)){
            joueurVaJouer.setUno(uno);
          }
          // thread safe
          this.lobbys.remove(numlobby);
          // pour eviter des conflits on s assure que le lobby contient toujours un element
          if(this.lobbys.size() == 0){
            this.lobbys.add(new ArrayList<JoueurInterface>());
          }
          System.out.println("une partie commence");
        }
      }
    }
    // on retourne le joueur au client ayant appele la methode
    return j;
  }

  /**
   * Methode permettant de savoir le nombre de lobbys actuellement en attente
   */
  public int getLobbys() throws RemoteException {
	List<List<JoueurInterface>> result = this.lobbys;
    return result.size();
  }

  /**
   * Methode permettant de verifier qu un pseudo n est pas deja present dans un lobby
   * @param name, l identifiant du futur joueur
   * @param i, le numero du lobby qu il faut verifier
   */
  private boolean checkName(String name,int i) throws RemoteException{
    for(JoueurInterface j : lobbys.get(i)){
      if(j.getId().equals(name)){
        return false;
      }
    }
    return true;
  }
}
