/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.entity;

import com.rmi.intf.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Uno extends UnicastRemoteObject implements UnoInterface {

  private List<JoueurInterface> joueurs = new ArrayList<JoueurInterface>();
  private List<CarteInterface> talon = new ArrayList<CarteInterface>();
  private List<CarteInterface> pioche = new ArrayList<CarteInterface>();
  private final List<String> couleurs = new ArrayList<String>(Arrays.asList("Rouge","Bleu","Jaune","Vert","Noire"));
  private final List<String> symboles = new ArrayList<String>(Arrays.asList("+2","sens","interdit","+4","couleur"));
  private List<String> idJoueursPret = new ArrayList<String>();
  private volatile Boolean GameOver = false;
  private volatile JoueurInterface courant;
  private String couleurChoisie;
  private boolean sens = true; //true pour sens horraire, false pour anti-horraire.
  private MessageInterface messageCommun;

  public Uno(List<JoueurInterface> Listjoueurs) throws RemoteException{
    super();
    messageCommun = new Message("");
    for (int i=0;i<Listjoueurs.size();i++) {
      switch (i) {
        case 0:
          Listjoueurs.get(i).setRight(Listjoueurs.get(i+1));
          break;
        default:
          if(i == Listjoueurs.size()-1){
            Listjoueurs.get(i).setLeft(Listjoueurs.get(i-1));
            Listjoueurs.get(i).setRight(Listjoueurs.get(0));
            Listjoueurs.get(0).setLeft(Listjoueurs.get(i));
          }else{
            Listjoueurs.get(i).setLeft(Listjoueurs.get(i-1));
            Listjoueurs.get(i).setRight(Listjoueurs.get(i+1));
          }
          break;
      }
      this.joueurs.add(Listjoueurs.get(i));
    }
    for(int j=0;j<couleurs.size()-1;j++){
      for(int z=0;z<10;z++){
        CarteNumero carte = new CarteNumero(couleurs.get(j),z);
        if(z != 0){
          CarteNumero carte2 = new CarteNumero(couleurs.get(j),z);
          this.pioche.add(carte2);
        }
        this.pioche.add(carte);
      }
      for (int m=0;m<3;m++){
        CarteAction carteAction = new CarteAction(couleurs.get(j),symboles.get(m));
        CarteAction carteAction2 = new CarteAction(couleurs.get(j),symboles.get(m));
        this.pioche.add(carteAction);
        this.pioche.add(carteAction2);
      }
    }
    for(int h=0;h<4;h++){
      CarteAction carteJoker = new CarteAction(couleurs.get(couleurs.size()-1),symboles.get(symboles.size()-2));
      CarteAction carteJoker2 = new CarteAction(couleurs.get(couleurs.size()-1),symboles.get(symboles.size()-1));
      this.pioche.add(carteJoker);
      this.pioche.add(carteJoker2);
    }
  }

  public void InitGame() throws RemoteException{
	  this.melangerList(this.pioche);
    for(JoueurInterface j : this.joueurs){
      for(int i=0;i<7;i++){
        j.piocher(this.pioche.remove(0));
      }
    }
    while(this.pioche.get(0).getClass() == CarteAction.class){
      this.melangerList(this.pioche);
    }
    CarteInterface c = this.pioche.remove(0);
    this.couleurChoisie = c.getCouleur();
    this.talon.add(c);
    this.courant = this.joueurs.get(0);
  }

  private boolean effetCarte(JoueurInterface j,CarteInterface carte) throws RemoteException{
    if(carte.getSymbole().equals("sens")){
      changeSens();
    }
    if(carte.getSymbole().equals("+2")){
      if(this.sens){
        j.getRight().piocher(this.pioche.remove(0));
        j.getRight().piocher(this.pioche.remove(0));
      }else{
        j.getLeft().piocher(this.pioche.remove(0));
        j.getLeft().piocher(this.pioche.remove(0));
      }
      return true;
    }
    if(carte.getSymbole().equals("interdit")){
      return true;
    }
    return false;
  }

  private void effetCarteNoire(JoueurInterface j,CarteInterface carte,String col) throws RemoteException{
    if(carte.getSymbole().equals("couleur")){
      this.couleurChoisie = col;
    }
    if(carte.getSymbole().equals("+4")){
      if(this.sens){
        j.getRight().piocher(this.pioche.remove(0));
        j.getRight().piocher(this.pioche.remove(0));
        j.getRight().piocher(this.pioche.remove(0));
        j.getRight().piocher(this.pioche.remove(0));
      }else{
        j.getLeft().piocher(this.pioche.remove(0));
        j.getLeft().piocher(this.pioche.remove(0));
        j.getLeft().piocher(this.pioche.remove(0));
        j.getLeft().piocher(this.pioche.remove(0));
      }
    }
  }

  public boolean JouerCarte(String id,CarteInterface carte,String col, boolean aPioche) throws RemoteException{
    JoueurInterface j = getJoueurByID(id);
    boolean carteValide = false;
    if(!this.GameOver){
      if(j == courant){
        CarteInterface last = this.talon.get(this.talon.size()-1);
        if(this.pioche.size()<=4){
          this.talonIntoPioche();
        }
        if(carte == null){
          CarteInterface cartePiocher = this.pioche.remove(0);
          j.piocher(cartePiocher);
          return true;
        }else{
          if(j.contient(carte)){
            if(carte.getCouleur().equals("Noire")){
              if(!aPioche){
                this.effetCarteNoire(j,carte,col);
                this.CarteJouer(j,carte,true);
                this.messageCommun.setMessage(id + " a joué la carte " + carte.affiche() +", c'est au tour du joueur " + this.courant.getId());
                return true;
              }
            }else{
              if(carte.getCouleur().equals(couleurChoisie)){
                if(carte.getClassName().equals("CarteAction")){
                  if(this.effetCarte(j,carte)){
                    this.CarteJouer(j,carte,true);
                    this.messageCommun.setMessage(id + " a joué la carte " + carte.affiche() +", c'est au tour du joueur " + this.courant.getId());
                    return true;
                  }
                }
                carteValide = true;
              }else{
                if(carte.getClassName().equals(last.getClassName())){
                  if(carte.getClassName().equals("CarteAction")){
                    if(carte.getSymbole().equals(last.getSymbole())){
                      this.couleurChoisie = carte.getCouleur();
                      if(this.effetCarte(j,carte)){
                        this.CarteJouer(j,carte,true);
                        this.messageCommun.setMessage(id + " a joué la carte " + carte.affiche() +", c'est au tour du joueur " + this.courant.getId());
                        return true;
                      }
                    }
                  }
                  if(carte.getClassName().equals("CarteNumero")){
                    if(carte.getNumero() == last.getNumero()){
                      this.couleurChoisie = carte.getCouleur();
                      carteValide = true;
                    }
                  }
                }
              }
            }
          }
        }
        if(carteValide){
          this.CarteJouer(j,carte,false);
          this.messageCommun.setMessage(id + " a joué la carte " + carte.affiche() +", c'est au tour du joueur " + this.courant.getId());
          return true;
        }
      }
    }
    if(aPioche){
      changeJoueur();
      this.messageCommun.setMessage(id +" ne peut pas jouer, c'est au tour du joueur " + this.courant.getId());
      return true;
    }
    return false;
  }

  public void CarteJouer(JoueurInterface j,CarteInterface c,boolean pass) throws RemoteException{
    this.talon.add(c);
    j.jouer(c);
    if(j.getMain().size() == 0){
      this.messageCommun.setMessage("Le joueur " + j.getId() + " a terminé!");
      j.getLeft().setRight(j.getRight());
      j.getRight().setLeft(j.getLeft());
      if(j.getLeft().getLeft() == j.getLeft()){
        this.messageCommun.setMessage("La partie est terminée.");
        this.GameOver = true;
        return;
      }
    }
    if(pass){
      if(this.sens){
        this.courant = this.courant.getRight().getRight();
      }else{
        this.courant = this.courant.getLeft().getLeft();
      }
    }else{
      changeJoueur();
    }
  }

  public CarteInterface peutJouer(JoueurInterface j) throws RemoteException{
    CarteInterface last = this.talon.get(this.talon.size()-1);
    for(CarteInterface c : j.getMain()){
      if(c.getCouleur().equals("Noire")){
        return c;
      }
      if(c.getCouleur().equals(this.couleurChoisie)){
        return c;
      }else{
        if(c.getClassName().equals(last.getClassName())){
          if(c.getClassName().equals("CarteAction")){
            if(c.getSymbole().equals(last.getSymbole())){
              return c;
            }
          }
          if(c.getClassName().equals("CarteNumero")){
            if(c.getNumero() == last.getNumero()){
              return c;
            }
          }
        }
      }
    }
    return null;
  }

  public void changeJoueur() throws RemoteException{
    if(this.sens){
      this.courant = this.courant.getRight();
    }else{
      this.courant = this.courant.getLeft();
    }
  }

  public void talonIntoPioche(){
    this.melangerList(this.talon);
    for(CarteInterface carte : this.talon){
      this.pioche.add(carte);
    }
    this.talon.clear();
  }

  public List<JoueurInterface> getJoueurs() throws RemoteException{
	  return joueurs;
  }

  public JoueurInterface getJoueur(int i) throws RemoteException{
	  return joueurs.get(i);
  }

  public synchronized JoueurInterface getJoueurByID(String id) throws RemoteException{
    for(JoueurInterface j : this.joueurs){
      if(j.getId().equals(id)){
        return j;
      }
    }
    return null;
  }

  public List<CarteInterface> getTalon(){
	  return talon;
  }

  public List<CarteInterface> getPioche(){
	  return pioche;
  }

  public void changeSens(){
    if(this.sens){
      this.sens = false;
    }else{
      this.sens = true;
    }
  }

  public boolean isGameOver(){
	  return GameOver;
  }

  public void setGameOver(){
	  this.GameOver = true;
  }

  public JoueurInterface getCourant() throws RemoteException{
	  return courant;
  }

  public String getCouleurChoisie(){
    return this.couleurChoisie;
  }

  public void setCourant(JoueurInterface newCourant) throws RemoteException{
	  courant = newCourant;
  }

  public boolean getSens(){
	  return sens;
  }

  public void melangerList(List<CarteInterface> aMelanger){
	  Collections.shuffle(aMelanger);
  }

  public synchronized MessageInterface getMess() throws RemoteException{
    return this.messageCommun;
  }

  public void setMess(MessageInterface m) throws RemoteException{
    this.messageCommun = m;
  }

  public void joueurPret(String id){
    if(!this.idJoueursPret.contains(id)){
      this.idJoueursPret.add(id);
    }
  }

  public boolean tousPret() throws RemoteException{
    if(this.idJoueursPret.size() == 4){
      return true;
    }
    return false;
  }

}
