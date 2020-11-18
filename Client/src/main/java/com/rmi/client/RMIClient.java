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
      mess = mInterface.getMessageCommun(this.monUnoActu);
      Thread thread = new Thread(() -> {
        while (!endThread) {
          try{
            if(!mess.getMessage().equals(mInterface.getMessageCommun(this.monUnoActu).getMessage())){
              System.out.println(mess.getMessage());
              mess = mInterface.getMessageCommun(this.monUnoActu);
            }
          } catch (Exception e) {
          }
        }
      });
      thread.start();

      while(this.monUnoActu == null){
        this.monUnoActu = mInterface.start(this.Id);
      }
      for (CarteInterface c : mInterface.getMyCards(this.Id,this.monUnoActu)) {
        this.main.add(c);
      }
      System.out.println("c'est au tour du joueur " + mInterface.getCourant(this.monUnoActu).getId() + " de jouer");
      while(!mInterface.GameOver(this.monUnoActu)){
        if(mInterface.getCourant(this.monUnoActu).getId().equals(pseudo)){
          System.out.println();
          System.out.println("la couleure demandée est " + mInterface.getCouleurActu(this.monUnoActu));
          System.out.println();
          System.out.println("la dernière carte du talon est " + mInterface.getLastTalon(this.monUnoActu).affiche());
          System.out.println();
          System.out.println("Voici votre main");
          this.main.clear();
          for (CarteInterface c : mInterface.getMyCards(this.Id,this.monUnoActu)) {
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
          MessageInterface temp;
          if(card == -1){
            temp = mInterface.playCard(Id,this.monUnoActu,null,null);
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
              temp = mInterface.playCard(Id,this.monUnoActu,main.get(card),myColChoice);
            }else{
              temp = mInterface.playCard(Id,this.monUnoActu,main.get(card),null);
            }
          }
          if(temp != null){
            System.out.println(temp.getMessage());
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
