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
  // attribut representant le joueur distant
  private JoueurInterface moi;
  // attribut representant l id(pseudo) du joueur
  private String Id;
  // attribut representant l ensemble des cartes composant la main du joueur
  private List<CarteInterface> main = new ArrayList<CarteInterface>();

  private void getMessage(){
    try {
      String oldMessage = mess.getMessage();
      mess = moi.getMess();
      if(!mess.getMessage().equals(oldMessage)){
        System.out.println(this.mess.getMessage());
      }
    } catch(Exception e) {
      System.out.println(e);
    }
  }

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
      moi = mInterface.joinGame(pseudo);
      mess = moi.getMess();
      System.out.println(mess.getMessage());
      // tant que le serveur n a pas affecte de uno a ce joueur
      while(this.moi.getUno() == null){
        getMessage();
      }
      // le jeu commence
      System.out.println("c'est au tour du joueur " + this.moi.getUno().getCourant().getId() + " de jouer");
      // tant que le jeu n est pas finis
      while(!this.moi.getUno().isGameOver()){
        getMessage();
        // si le joueur est le joueur courant du uno
        if(this.moi.getUno().getCourant().getId().equals(this.Id)){
          System.out.println();
          System.out.println("la couleur demandée est " + this.moi.getUno().getCouleurChoisie());
          System.out.println();
          System.out.println("la dernière carte du talon est " + this.moi.getUno().getTalon().get(this.moi.getUno().getTalon().size()-1).affiche());
          System.out.println();
          System.out.println("Voici votre main");
          this.main.clear();
          // recuperation des cartes du joueur
          for (CarteInterface c : this.moi.getMain()) {
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
            CarteInterface test = this.moi.getUno().peutJouer(moi);
            if(test != null){
              System.out.println("La carte " + test.affiche() + " peut être jouée");
            }else{
              // le joueur ne peut pas jouer, il pioche
              this.moi.getUno().JouerCarte(this.Id,null,null,false);
              // peut il jouer la carte qu il vient de piocher
              carteJouee = this.moi.getUno().JouerCarte(this.Id,this.moi.getUno().getCourant().getMain().get(this.moi.getUno().getCourant().getMain().size()-1),null,true);
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
              carteJouee = this.moi.getUno().JouerCarte(this.Id,main.get(card),myColChoice,false);
            }else{
              // sinon il joue la carte sans preciser de couleur
              carteJouee = this.moi.getUno().JouerCarte(this.Id,main.get(card),null,false);
            }
          }
          // la variable carte jouee est fausse, le joueur a donc tente de jouer une carte invalide
          if(!carteJouee){
            System.out.println("La carte ne peut pas être jouée");
          }
        }
      }
      System.out.println("Le jeu est terminé !");

    } catch (Exception e) {
      System.out.println("A problem occured with server: " + e.toString());
      e.printStackTrace();
    }
  }
}
