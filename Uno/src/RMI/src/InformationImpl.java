package RMI.src;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class InformationImpl extends UnicastRemoteObject implements Information {

  private static final long serialVersionUID = 2674880711467464646L;

  protected InformationImpl() throws RemoteException {
    super();
  }

  public String getInformation() throws RemoteException {
    System.out.println("Invocation de la m�thode getInformation()");
    return "POmme";
  }
 /* public static void main(String[] args) {
	  try {
	    if (System.getSecurityManager() == null) {
	      System.setSecurityManager(new RMISecurityManager());
	    }
	  } catch (Exception e) {
	     e.printStrackTrace();
	  }
	}*/
}