/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.entity;

import com.rmi.intf.CarteInterface;
import com.rmi.intf.UnoInterface;
import com.rmi.intf.JoueurInterface;

import java.util.ArrayList;
import java.util.List;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * Classe representant un joueur, un joueur joue au uno avec d autres joueurs et possede des cartes de ce dernier
 */
public class Joueur extends UnicastRemoteObject implements JoueurInterface{
  // attribut representant l identifiant(pseudo) du joueur
  private String identifiant;
  // attribut representant le joueur a la gauche de lui meme
  private JoueurInterface left;
  // attribut representant le joueur a la droite de lui meme
  private JoueurInterface right;
  // attribut representant les cartes composant la main du joueur
  private List<CarteInterface> main = new ArrayList<CarteInterface>();
  // attribut representant le uno auquel le joueur est attribue
  private UnoInterface myUno;

  /**
  * Constructeur de la class Joueur
  * Cree un joueur avec les attributs ci-dessous
  * @param id, l identifiant du joueur
  * @param l, le joueur a gauche
  * @param r, le joueur a droite
  */
  public Joueur(String id,JoueurInterface l, JoueurInterface r) throws RemoteException{
    this.identifiant = id;
    this.left = l;
    this.right = r;
  }

  /**
  * Acceseur de l attribut id
  * @return l identifiant du joueur
  */
  public String getId(){
    return this.identifiant;
  }

  /**
  * Acceseur de l attribut left
  * @return le joueur etant a gauche
  */
  public JoueurInterface getLeft(){
    return this.left;
  }

  /**
  * Setter de l attribut left
  * @param j, le joueur qui se place a la gauche de this
  */
  public void setLeft(JoueurInterface j){
    this.left = j;
  }

  /**
  * Acceseur de l attribut right
  * @return le joueur etant a droite
  */
  public JoueurInterface getRight(){
    return this.right;
  }

  /**
  * Setter de l attribut right
  * @param j, le joueur qui se place a la droite de this
  */
  public void setRight(JoueurInterface j){
    this.right = j;
  }

  /**
  * Acceseur de l attribut main
  * @return la liste des cartes composant la main du joueur
  */
  public List<CarteInterface> getMain(){
    return this.main;
  }

  /**
  * Methode permettant de retirer une carte de la main du joueur et de la retourner si cette derniere contient la carte
  * @param carte, la carte a verifier et a enlever si elle appartient a la main du joueur
  * @return la carte retiree si elle appartenait au joueur, null sinon
  */
  public CarteInterface jouer(CarteInterface carte) throws RemoteException{
    for(CarteInterface c : this.main){
      if(c.equals(carte)){
        this.main.remove(c);
        return c;
      }
    }
    return null;
  }

  /**
  * Methode permettant de verifier si une carte est contenue dans la main du joueur
  * @param carte, la carte a verifier
  * @return true si la carte est contenue dans la main du joueur, false sinon
  */
  public boolean contient(CarteInterface carte) throws RemoteException{
    for(CarteInterface c : this.main){
      if(c.getNumero() == carte.getNumero() && c.getCouleur().equals(carte.getCouleur()) && c.getSymbole().equals(carte.getSymbole())){
        return true;
      }
    }
    return false;
  }

  /**
  * Methode permettant d ajouter une carte dans la main du joueur
  * @param c, la carte a ajouter
  */
  public void piocher(CarteInterface c){
    this.main.add(c);
  }

  /**
  * Methode permettant d afficher les cartes du joueur de facon user-friendly
  */
  public void montreMain() throws RemoteException{
    for(CarteInterface c : this.main){
      System.out.println(c.affiche());
    }
  }

  /**
  * Acceseur de l attribut myUno
  * @return le uno auquel le joueur est associe, null si le joueur n est pas encore associe a un uno
  */
  public UnoInterface getUno() throws RemoteException{
    return this.myUno;
  }

  /**
  * Setter de l attribut myUno
  * @param u, le uno auquel le joueur est associe
  */
  public void setUno(UnoInterface u) throws RemoteException{
    this.myUno = u;
  }
}
