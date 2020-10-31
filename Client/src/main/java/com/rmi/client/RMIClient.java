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
      mess = mInterface.getMess();
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

      System.out.println("Rentrez un pseudo");
      String pseudo = sc.nextLine();
      mInterface.joinGame(pseudo);
      this.Id = pseudo;
      while(!mInterface.start()){}
      this.main = mInterface.getMyCards(Id);
      System.out.println("Voici votre main");
      int i = 0;
      for (CarteInterface c : this.main) {
        System.out.println(i + ": "+ c.affiche());
        ++i;
      }
      System.out.println("c'est au tour du joueur " + mInterface.getCourant().getId() + " de jouer");
      System.out.println("la dernière carte du talon est " + mInterface.getLastTalon().affiche());
      while(!mInterface.GameOver()){
        if(mInterface.getCourant().getId().equals(pseudo)){
          System.out.println("Sélectionnez le numéro de votre carte");
          int card = sc.nextInt();
          MessageInterface temp = mInterface.playCard(Id,main.get(card),null);
          if(temp != null){
            System.out.println(temp.getMessage());
          }
          System.out.println("Voici votre main");
          i = 0;
          for (CarteInterface c : this.main) {
            System.out.println(i + ": "+ c.affiche());
            ++i;
          }
        }
      }
    } catch (Exception e) {
      System.out.println("A problem occured with server: " + e.toString());
      e.printStackTrace();
    }
  }
}
