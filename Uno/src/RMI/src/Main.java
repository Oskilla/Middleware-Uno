package RMI.src;


import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
	
	public static void main(String[] args) {
	    try {
	      LocateRegistry.createRegistry(1099);

	      System.out.println("Mise en place du Security Manager ...");
	     /* if (System.getSecurityManager() == null) {
	        System.setSecurityManager(new RMISecurityManager());
	      }*/

	      InformationImpl informationImpl = new InformationImpl();

	      String url =  InetAddress.getLocalHost().getHostAddress() + "/TestRMI";
	      System.out.println("Enregistrement de l'objet avec l'url : " + url);
	      Naming.rebind(url, informationImpl);

	      System.out.println("Serveur lanc�");
	    } catch (RemoteException e) {
	      e.printStackTrace();
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    } catch (UnknownHostException e) {
	      e.printStackTrace();
	    }
	  }
	
	/*public static void main(String[] args) {
		 /* try {
		    if (System.getSecurityManager() == null) {
		      System.setSecurityManager(new RMISecurityManager());
		    }
		    
		    InformationImpl informationImpl = new InformationImpl();
		    String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/TestRMI";
		    String url2 = "rmi://localhost:8080/testRMI";
		    System.out.println("Enregistrement de l'objet avec l'url : " + url);
		    System.out.println("Enregistrement de l'objet avec l'url : " + url2);
		    System.setProperty("java.security.policy","file:./myPolicy.policy");

		    //System.setProperty("java.rmi.server.hostname",url2);
		    Naming.rebind(url, informationImpl);

		    System.out.println("Serveur lanc�");
		  } catch (RemoteException e) {
		    e.printStackTrace();
		  } catch (MalformedURLException e) {
		    e.printStackTrace();
		  } catch (UnknownHostException e) {
		    e.printStackTrace();
		  }/
		

		   try {

			  java.rmi.registry.LocateRegistry.createRegistry(1099);

		      System.out.println("Mise en place du Security Manager ...");
		      System.setSecurityManager(new java.rmi.RMISecurityManager());

		      Information testRMIServer = new InformationImpl();

		      System.out.println("Enregistrement du serveur");

		      Naming.rebind("rmi://"+java.net.InetAddress.getLocalHost()+
		         "/TestRMI",testRMIServer);

		      // Naming.rebind(";rmi://localhost/TestRMI", testRMIServer);


		      System.out.println("Serveur lanc�");

		   } catch (Exception e) {
		      System.out.println("Exception captur�e: " + e.getMessage());
		   }

		}*/

}

