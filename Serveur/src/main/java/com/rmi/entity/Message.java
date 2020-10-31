package com.rmi.entity;

import com.rmi.intf.MessageInterface;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Message extends UnicastRemoteObject implements MessageInterface{
  private String message;

  public Message(String m) throws RemoteException{
    this.message = m;
  }

  public void setMessage(String Mess){
    this.message = Mess;
  }

  public String getMessage(){
    return this.message;
  }
}
