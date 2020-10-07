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
  private boolean sens = true; //true pour sens horraire, false pour anti-horraire.

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
	  this.GameOver = false;
  }

  public void InitGame(List<String> joueurs){
		couleurs.add("Rouge");
		couleurs.add("Bleu");
		couleurs.add("Jaune");
		couleurs.add("Vert");
	    for (int i=0;i<couleurs.size() ; i++) {
	    	for(int j=0; j<=9; j++) {
	    		CarteNumero crt = new CarteNumero(couleurs.get(i),"carte "+couleurs.get(i)+" numéro "+j,j);
	    		pioche.add(crt);
	    	}
	    	CarteAction crta1 = new CarteAction(couleurs.get(i),"Carte joker","Joker");
	    	CarteAction crta2 = new CarteAction(couleurs.get(i),"Carte +4","+4"); 
	    	CarteAction crta3 = new CarteAction(couleurs.get(i),"Carte +2 "+couleurs.get(i),"+2");
	    	CarteAction crta4 = new CarteAction(couleurs.get(i),"Carte +2 "+couleurs.get(i),"+2");
	    	CarteAction crta7 = new CarteAction(couleurs.get(i),"Carte passe tour "+couleurs.get(i),"Passe tour");
	    	CarteAction crta8 = new CarteAction(couleurs.get(i),"Carte passe tour "+couleurs.get(i),"Passe tour");
	    	CarteAction crta5 = new CarteAction(couleurs.get(i),"Carte changement sens "+couleurs.get(i),"Change Sens");
	    	CarteAction crta6 = new CarteAction(couleurs.get(i),"Carte changement sens "+couleurs.get(i),"Change Sens");
	    	pioche.add(crta1);
	    	pioche.add(crta2);
	    	pioche.add(crta3);
	    	pioche.add(crta4);
	    	pioche.add(crta5);
	    	pioche.add(crta6);
	    	pioche.add(crta7);
	    	pioche.add(crta8);
	    }
	    this.melangerList(this.pioche);
    for(int i = 1; i<=joueurs.size();i++) {
    	Joueur jo = new Joueur(joueurs.get(i),this);
    	this.joueurs.add(jo);
    }
    this.courant = this.joueurs.get(0);
  }
  
 public void tourDeJeu() {
	 //implementer un timer.
	 //action listener pour definir la carte jouer par this.courant.
	 Carte c = courant.getMain().get(1);
	 if(c.getCouleur().equals(talon.get(talon.size()).getCouleur())) { //il faut aussi tester que si la carte posé au tour précédent n'est pas un passage de tour ou un +2 ou un +4
		 this.courant.jouer(c);
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
  public List<Carte> getTalon(){
	  return talon;
  }
  public List<Joueur> getJoueurs(){
	  return joueurs;
  }
  public Joueur getJoueur(int i) {
	  return joueurs.get(i);
  }
  public Joueur getCourant() {
	  return courant;
  }
  public void setCourant(Joueur newCourant) {
	  courant = newCourant;
  }
  public boolean isGameOver() {
	  return GameOver;
  }
  public boolean getSens() {
	  return sens;
  }
  public void setGameOver() {
	  this.GameOver = true;
  }
}
