package RMI.src;



import java.rmi.*;

public interface Information extends Remote {

   public String getInformation() throws RemoteException;

}