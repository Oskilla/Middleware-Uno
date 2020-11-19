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

public class RMIClient {
  private MessageInterface mess;
  private UnoInterface monUnoActu;
  private boolean endThread = false;
  private String Id;
  private List<CarteInterface> main = new ArrayList<CarteInterface>();
  public RMIClient(){
    try {
      Scanner sc = new Scanner(System.in);
      RMIServerInterface mInterface = (RMIServerInterface) Naming.lookup("rmi://localhost:1099/Server_1099");
      System.out.println("vous avez rejoins le serveur");
      System.out.println("Rentrez un pseudo");
      String pseudo = sc.nextLine();
      this.Id = pseudo;
      mInterface.joinGame(pseudo);
      mess = mInterface.getMessageCommun();
      System.out.println(mess.getMessage());

      Thread thread = new Thread(() -> {
        while (!endThread) {
          try{
            String oldMessage = mess.getMessage();
            if(this.monUnoActu == null){
              mess = mInterface.getMessageCommun();
              if(!mess.getMessage().equals(oldMessage)){
                System.out.println(this.mess.getMessage());
              }
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

      while(this.monUnoActu == null){
        this.monUnoActu = mInterface.start(this.Id);
      }
      for (CarteInterface c : this.monUnoActu.getJoueurByID(this.Id).getMain()) {
        this.main.add(c);
      }
      System.out.println("c'est au tour du joueur " + this.monUnoActu.getCourant().getId() + " de jouer");
      while(!this.monUnoActu.isGameOver()){
        if(this.monUnoActu.getCourant().getId().equals(this.Id)){
          System.out.println();
          System.out.println("la couleure demandée est " + this.monUnoActu.getCouleurChoisie());
          System.out.println();
          System.out.println("la dernière carte du talon est " + this.monUnoActu.getTalon().get(this.monUnoActu.getTalon().size()-1).affiche());
          System.out.println();
          System.out.println("Voici votre main");
          this.main.clear();
          for (CarteInterface c : this.monUnoActu.getJoueurByID(this.Id).getMain()) {
            this.main.add(c);
          }
          int i = 0;
          for (CarteInterface c : this.main) {
            System.out.println(i + ": "+ c.affiche());
            ++i;
          }
          System.out.println();
          System.out.println("Sélectionnez le numéro de votre carte, ou -1 si vous ne pouvez pas jouer");
          int card = sc.nextInt();
          boolean carteJouee = false;
          if(card == -1){
            CarteInterface test = this.monUnoActu.peutJouer(this.monUnoActu.getJoueurByID(this.Id));
            if(test != null){
              System.out.println("La carte " + test.affiche() + " peut être jouée");
            }else{
              this.monUnoActu.JouerCarte(this.Id,null,null,false);
              carteJouee = this.monUnoActu.JouerCarte(this.Id,this.monUnoActu.getCourant().getMain().get(this.monUnoActu.getCourant().getMain().size()-1),null,true);
            }
          }else{
            if(main.get(card).getCouleur().equals("Noire") && main.get(card).getSymbole().equals("couleur")){
              System.out.println("choisissez une couleure parmis, 1- Rouge, 2- Bleu, 3- Jaune, 4- Vert");
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
              carteJouee = this.monUnoActu.JouerCarte(this.Id,main.get(card),myColChoice,false);
            }else{
              carteJouee = this.monUnoActu.JouerCarte(this.Id,main.get(card),null,false);
            }
          }
          if(!carteJouee){
            System.out.println("La carte ne peut pas être jouée");
          }
        }
      }
      this.endThread = true;
      thread.join();
      System.out.println("Le jeu est terminé !");
    } catch (Exception e) {
      System.out.println("A problem occured with server: " + e.toString());
      e.printStackTrace();
    }
  }
}
