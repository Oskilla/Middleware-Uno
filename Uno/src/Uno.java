import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Uno {

  private List<Joueur> joueurs = new ArrayList<Joueur>();
  private List<Carte> talon = new ArrayList<Carte>();
  private List<Carte> pioche = new ArrayList<Carte>();
  private List<String> couleurs = new ArrayList<String>();
  private Boolean GameOver = false;
  private Joueur courant;
  private int sens = 1;

  //je suis pas sûr de la pertinence de ce constructeur
  //peut être qu'il faudrait juste faire un appel a init
  public Uno(List<Carte> jeu, List<Joueur> joueurs){
    for (int i=0; i<jeu.size();i++) {
      this.pioche.add(jeu.get(i));
    }
    for (int j=0;j<joueurs.size();j++) {
      this.joueurs.add(joueurs.get(j));
    }
  }
  public Uno(List<String> joueurs) {
	  InitGame(joueurs);
  }

  public void InitGame(List<String> joueurs){
	couleurs.add("Rouge");
	couleurs.add("Bleu");
	couleurs.add("Jaune");
	couleurs.add("Vert");
    for (int i=0;i<couleurs.size() ; i++) {
    	for(int j=0; j<=9; j++) {
    		CarteNumero crt = new CarteNumero(couleurs.get(i),"",j);
    		pioche.add(crt);
    	}
    	CarteAction crta1 = new CarteAction(couleurs.get(i),"","Joker");
    	CarteAction crta2 = new CarteAction(couleurs.get(i),"","+4");
    	CarteAction crta3 = new CarteAction(couleurs.get(i),"","+2");
    	CarteAction crta4 = new CarteAction(couleurs.get(i),"","+2");
    	CarteAction crta5 = new CarteAction(couleurs.get(i),"","Change Sens");
    	CarteAction crta6 = new CarteAction(couleurs.get(i),"","Change Sens");
    	pioche.add(crta1);
    	pioche.add(crta2);
    	pioche.add(crta3);
    	pioche.add(crta4);
    	pioche.add(crta5);
    	pioche.add(crta6);
    }
    this.melangerList(this.pioche);
    
    for(int i = 1; i<=joueurs.size();i++) {
    	Joueur jo = new Joueur(joueurs.get(i),this);
    	this.joueurs.add(jo);
    }
  }
  public void melangerList(List<Carte> aMelanger) {
	  Collections.shuffle(aMelanger);
  }
  public boolean appartientAList(List<Carte>cartes, Carte crt) {
	  return cartes.contains(crt);
  }
  public Carte retirerList(List<Carte> cartes, int i) {
	  return cartes.remove(i); 
  }
  public List<Carte> getPioche(){
	  return pioche;
  }
}
