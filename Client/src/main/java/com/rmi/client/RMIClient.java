/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.client;

import com.rmi.intf.*;
import java.rmi.Naming;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe representant le client, c est dans cette classe que l on vas appeler les methodes des objets remote du serveur
 */
public class RMIClient {
  // attribut permettant d avoir les messages communs entre tout les joueurs
  private MessageInterface mess;
  // attribut representant le uno dans lequel le joueur est present
  private UnoInterface monUnoActu;
  // atribut permettant d arreter le thread qui affiche les messages communs en continue
  private boolean endThread = false;
  // attribut representant l id(pseudo) du joueur
  private String Id;
  // attribut representant l ensemble des cartes composant la main du joueur
  private List<CarteInterface> main = new ArrayList<CarteInterface>();

  /**
  * Constructeur de la classe RMICLient
  * appel les objets remote et interargit avec eux
  */
  public RMIClient(){

    try {
      Scanner sc = new Scanner(System.in);
      // connexion à l hote distant
      RMIServerInterface mInterface = (RMIServerInterface) Naming.lookup("rmi://localhost:1099/Server_1099"); // remplacez ici le port du serveur
      System.out.println("vous avez rejoins le serveur");
      System.out.println("Rentrez un pseudo");
      String pseudo = sc.nextLine();
      this.Id = pseudo;
      // appel de la methode qui permet de rejoindre des joueurs afin de lancer une partie
      mInterface.joinGame(pseudo);
      mess = mInterface.getMessageCommun();
      System.out.println(mess.getMessage());

      // Thread permettant d afficher les messages communs a tout les joueurs de ce uno en continue, tant que le jeu n est pas termine
      Thread thread = new Thread(() -> {
        while (!endThread) {
          try{
            String oldMessage = mess.getMessage();
            // si le joueur n est pas deja affecte a un uno
            if(this.monUnoActu == null){
              // on recupere le message du serveur
              mess = mInterface.getMessageCommun();
              // si le message est different de celui que l on a deja
              if(!mess.getMessage().equals(oldMessage)){
                System.out.println(this.mess.getMessage());
              }
            // si le joueur est deja affecte a un uno
            }else{
              mess = monUnoActu.getMess();
              if(!mess.getMessage().equals(oldMessage)){
                System.out.println(this.mess.getMessage());
              }
            }
          } catch (Exception e) {
            System.out.println(e);
          }
        }
      });
      thread.start();

      // tant que le serveur n a pas affecte de uno a ce joueur
      while(this.monUnoActu == null){
        this.monUnoActu = mInterface.start(this.Id);
      }

      // le jeu commence
      System.out.println("c'est au tour du joueur " + this.monUnoActu.getCourant().getId() + " de jouer");
      // tant que le jeu n est pas finis
      while(!this.monUnoActu.isGameOver()){
        // si le joueur est le joueur courant du uno
        if(this.monUnoActu.getCourant().getId().equals(this.Id)){
          System.out.println();
          System.out.println("la couleur demandée est " + this.monUnoActu.getCouleurChoisie());
          System.out.println();
          System.out.println("la dernière carte du talon est " + this.monUnoActu.getTalon().get(this.monUnoActu.getTalon().size()-1).affiche());
          System.out.println();
          System.out.println("Voici votre main");
          this.main.clear();
          // recuperation des cartes du joueur
          for (CarteInterface c : this.monUnoActu.getJoueurByID(this.Id).getMain()) {
            this.main.add(c);
          }
          int i = 0;
          // affichage des cartes du joueur
          for (CarteInterface c : this.main) {
            System.out.println(i + ": "+ c.affiche());
            ++i;
          }
          System.out.println();
          System.out.println("Sélectionnez le numéro de votre carte, ou -1 si vous ne pouvez pas jouer");
          int card = sc.nextInt();
          boolean carteJouee = false;
          // si le joueur a dis qu il ne pouvait pas jouer de carte
          if(card == -1){
            // verification qu il ne puisse vraiment pas jouer
            CarteInterface test = this.monUnoActu.peutJouer(this.monUnoActu.getJoueurByID(this.Id));
            if(test != null){
              System.out.println("La carte " + test.affiche() + " peut être jouée");
            }else{
              // le joueur ne peut pas jouer, il pioche
              this.monUnoActu.JouerCarte(this.Id,null,null,false);
              // peut il jouer la carte qu il vient de piocher
              carteJouee = this.monUnoActu.JouerCarte(this.Id,this.monUnoActu.getCourant().getMain().get(this.monUnoActu.getCourant().getMain().size()-1),null,true);
            }
          }else{
            // si la carte qu il a selectione est de couleur noire et que son symbole est couleur
            if(main.get(card).getCouleur().equals("Noire") && main.get(card).getSymbole().equals("couleur")){
              System.out.println("choisissez une couleur parmis, 1- Rouge, 2- Bleu, 3- Jaune, 4- Vert");
              int color = sc.nextInt();
              String myColChoice = "";
              switch (color) {
                case 1:
                  myColChoice = "Rouge";
                  break;
                case 2:
                  myColChoice = "Bleu";
                  break;
                case 3:
                  myColChoice = "Jaune";
                  break;
                case 4:
                  myColChoice = "Vert";
                  break;
              }
              // il joue la carte couleur, noire avec la couleur qu il a choisi
              carteJouee = this.monUnoActu.JouerCarte(this.Id,main.get(card),myColChoice,false);
            }else{
              // sinon il joue la carte sans preciser de couleur
              carteJouee = this.monUnoActu.JouerCarte(this.Id,main.get(card),null,false);
            }
          }
          // la variable carte jouee est fausse, le joueur a donc tente de jouer une carte invalide
          if(!carteJouee){
            System.out.println("La carte ne peut pas être jouée");
          }
        }
      }
      // arret du Thread pour les messages communs
      this.endThread = true;
      thread.join();
      System.out.println("Le jeu est terminé !");

    } catch (Exception e) {
      System.out.println("A problem occured with server: " + e.toString());
      e.printStackTrace();
    }
  }
}
