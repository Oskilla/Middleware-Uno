package com.rmi.client;

import com.rmi.intf.*;
import java.rmi.Naming;

public class RMIClient {
  private MessageInterface mess;
  public RMIClient() {
    try {
      RMIServerInterface mInterface = (RMIServerInterface) Naming.lookup("rmi://localhost:1099/Server_1099");
      mess = mInterface.getMess();
      mInterface.joinGame("Damien");
      while(mess.getMessage().equals(mInterface.getMess().getMessage())){

      }
      mess = mInterface.getMess();
      System.out.println(mess.getMessage());
    } catch (Exception e) {
      System.out.println("A problem occured with server: " + e.toString());
      e.printStackTrace();
    }
  }
}
