import java.util.ArrayList;
import java.util.List;

public class Joueur {

  private String identifiant;
  private List<Carte> main = new ArrayList<Carte>();
  private Uno leJeu;
 
  public Joueur(String id,Uno leJeu){
    this.identifiant = id;
    this.leJeu = leJeu;
    for (int i=0;i<this.leJeu.getPioche().size();i++) {
      main.add(this.leJeu.retirerList(this.leJeu.getPioche(),i));
    }
  }

  public String getId(){
    return this.identifiant;
  }

  public List<Carte> getMain(){
    return this.main;
  }

  public void jouer(Carte c){
    /*if(this.main.contains(c)){
      return c;
    }
    return null;*/
	//il faut réfléchir a comment géré la méthode jouer
	
	  
	//la méthode doit ce terminer ainsi
	int current = this.leJeu.getJoueurs().indexOf(this);
	if(this.leJeu.getSens()) {		
		this.leJeu.setCourant(this.leJeu.getJoueurs().get(current+1));
	}else {
		this.leJeu.setCourant(this.leJeu.getJoueurs().get(current+1));
	}
	this.leJeu.tourDeJeu();
	
  }

  public void piocher(Carte c){
    this.main.add(c);
  }
}
