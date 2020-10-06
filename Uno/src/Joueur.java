import java.util.ArrayList;
import java.util.List;

public class Joueur {

  private String identifiant;
  private List<Carte> main = new ArrayList<Carte>();

 
  public Joueur(String id,Uno leJeu){
    this.identifiant = id;
    for (int i=0;i<leJeu.getPioche().size();i++) {
      main.add(leJeu.retirerList(leJeu.getPioche(),i));
    }
  }

  public String getId(){
    return this.identifiant;
  }

  public List<Carte> getMain(){
    return this.main;
  }

  public Carte jouer(Carte c){
    if(this.main.contains(c)){
      return c;
    }
    return null;
  }

  public void piocher(Carte c){
    this.main.add(c);
  }
}
