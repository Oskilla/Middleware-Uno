package com.rmi.client;

import com.rmi.intf.*;
import java.rmi.Naming;

import java.util.Scanner;

public class RMIClient {
  private MessageInterface mess;
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
    } catch (Exception e) {
      System.out.println("A problem occured with server: " + e.toString());
      e.printStackTrace();
    }
  }
}
