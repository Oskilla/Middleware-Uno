package RMI.src;


import java.applet.*;
import java.awt.*;
import java.rmi.*;

public class AppletTestRMI extends Applet {

  private static final long serialVersionUID = 2674880711467464646L;

  private String s;

  public void init() {

    try { 
      Remote r = Naming.lookup("192.168.43.81/TestRMI");

      if (r instanceof Information) {
       s = ((Information) r).getInformation();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void paint(Graphics g) {
    super.paint(g);
    g.drawString("chaine retourn�e = "+s,20,20);
  }
}