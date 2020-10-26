package com.rmi.client;

import com.rmi.intf.*;
import java.rmi.Naming;

public class RMIClient {
  UnoInterface mInterface;
  public RMIClient() {
    try {
      mInterface = (UnoInterface) Naming.lookup("rmi://localhost/ImplMInterface_1099");
      System.out.println(mInterface.getCourant().getId());
    } catch (Exception e) {
      System.out.println("A problem occured with server: " + e.toString());
      e.printStackTrace();
    }
  }
}
