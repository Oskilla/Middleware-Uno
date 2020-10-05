import java.util.ArrayList;
import java.util.List;

public class Joueur {

  private String identifiant;
  private List<Carte> main = new ArrayList<Carte>();

  public Joueur(String id,List<Carte> cartes){
    this.identifiant = id;
    for (int i=0;i<cartes.size();i++) {
      main.add(cartes.get(i));
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
