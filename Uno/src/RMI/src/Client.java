package RMI.src;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Client {
	private static final long serialVersionUID = 2674880711467464646L;
	
	public static void main(String[] args) {

		/*  if (System.getSecurityManager() == null) {
		    System.setSecurityManager(new RMISecurityManager());
		  }*/

	    System.out.println("Lancement du client");

		try {
			
			Remote r = Naming.lookup("192.168.43.81/TestRMI");
		      System.out.println(r);
		      if (r instanceof Information) {
		        String s = ((Information) r).getInformation();
		        System.out.println("chaine renvoyee = " + s);
		      }
		    } catch (MalformedURLException e) {
		      e.printStackTrace();
		    } catch (RemoteException e) {
		      e.printStackTrace();
		    } catch (NotBoundException e) {
		      e.printStackTrace();
		    }
	
		    System.out.println("Fin du client");
	}
}
