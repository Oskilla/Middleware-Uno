package com.rmi.client;

import com.rmi.intf.*;
import java.rmi.Naming;

public class RMIClient {
  public RMIClient() {
    try {
      RMIServerInterface mInterface = (RMIServerInterface) Naming.lookup("rmi://localhost:1099/Server_1099");
      System.out.println(mInterface);
      System.out.println(mInterface.joinGame("Bastien"));
      System.out.println(mInterface.getUno().getCourant().getId());
      while(true){

      }
    } catch (Exception e) {
      System.out.println("A problem occured with server: " + e.toString());
      e.printStackTrace();
    }
  }
}
