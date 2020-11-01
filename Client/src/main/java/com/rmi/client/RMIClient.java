package com.rmi.client;

import com.rmi.intf.*;
import java.rmi.Naming;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RMIClient {
  private MessageInterface mess;
  private JoueurInterface courant;
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

      mess = mInterface.getMess();
      mInterface.joinGame(pseudo);
      Thread thread = new Thread(() -> {
        while (true) {
          try{
            if(!mess.getMessage().equals(mInterface.getMess().getMessage())){
              System.out.println(mess.getMessage());
              mess = mInterface.getMess();
            }
          } catch (Exception e) {
          }
        }
      });
      thread.start();

      while(!mInterface.start()){}
      for (CarteInterface c : mInterface.getMyCards(Id)) {
        this.main.add(c);
      }
      System.out.println("c'est au tour du joueur " + mInterface.getCourant().getId() + " de jouer");
      while(!mInterface.GameOver()){
        if(mInterface.getCourant().getId().equals(pseudo)){
          System.out.println();
          System.out.println("la couleure demandée est " + mInterface.getCouleurActu());
          System.out.println();
          System.out.println("la dernière carte du talon est " + mInterface.getLastTalon().affiche());
          System.out.println();
          System.out.println("Voici votre main");
          this.main.clear();
          for (CarteInterface c : mInterface.getMyCards(Id)) {
            this.main.add(c);
          }
          int i = 0;
          for (CarteInterface c : this.main) {
            System.out.println(i + ": "+ c.affiche());
            ++i;
          }
          System.out.println();
          System.out.println("Sélectionnez le numéro de votre carte");
          int card = sc.nextInt();
          MessageInterface temp = mInterface.playCard(Id,main.get(card),null);
          if(temp != null){
            System.out.println(temp.getMessage());
          }
        }
      }
    } catch (Exception e) {
      System.out.println("A problem occured with server: " + e.toString());
      e.printStackTrace();
    }
  }
}
